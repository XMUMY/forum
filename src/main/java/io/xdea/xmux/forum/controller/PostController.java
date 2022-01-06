package io.xdea.xmux.forum.controller;

import io.xdea.xmux.forum.dto.PostGrpcApi;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import io.xdea.xmux.forum.model.Post;
import io.xdea.xmux.forum.service.GroupService;
import io.xdea.xmux.forum.service.PostService;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.Date;
import java.util.List;

public abstract class PostController extends GroupController {

    private final PostService postService;

    protected PostController(GroupService groupService, PostService postService) {
        super(groupService);
        this.postService = postService;
    }

    // TODO: error logging

    @Override
    public void createPost(PostGrpcApi.CreatePostReq request,
                           StreamObserver<PostGrpcApi.CreatePostResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        Date nowTime = new Date();
        Post post = new Post()
                .withTitle(request.getTitle())
                .withBody(request.getBody())
                .withGroupId(request.getGroupId())
                .withUid(uid)
                .withCreateTime(nowTime)
                .withUpdateTime(nowTime)
                .withVote(0)
                .withBest(false)
                .withTopped(false);
        try {
            if (!postService.create(post))
                throw new Exception("postService.create returned false");
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("DB error").asException());
            return;
        }
        var resp = PostGrpcApi.CreatePostResp
                .newBuilder().setPostId(post.getId()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void removePost(PostGrpcApi.UpdatePostReq request,
                           StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        Post post = postService.getById(request.getPostId());
        // Check if the user has privilege to remove the post
        if (post == null) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Post does not exist").asException());
            return;
        }
        String postUid = post.getUid();
        if (postUid == null || !postUid.equals(uid)) {
            responseObserver.onError(Status.PERMISSION_DENIED
                    .withDescription("Not the owner of the post").asException());
            return;
        }

        // Soft delete
        try {
            if (!postService.softRemove(request.getPostId()))
                throw new Exception("postService.softRemove returned false");
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("DB error").asException());
            return;
        }
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getPost(PostGrpcApi.GetPostReq request, StreamObserver<PostGrpcApi.GetPostResp> responseObserver) {
        List<Integer> groupIdsList = request.getGroupIdsList();
        if (groupIdsList.size() == 0)
            groupIdsList = null;
        var posts = postService.get(request.getPageNo(), request.getPageSize(), groupIdsList);
        // ASK: is there better way to avoid copying?
        PostGrpcApi.GetPostResp.Builder respBuilder = PostGrpcApi.GetPostResp.newBuilder();
        posts.forEach(post -> {
            respBuilder.addPd(PostGrpcApi.PostDetails.newBuilder()
                    .setId(post.getId())
                    .setCreateTime(Timestamp.newBuilder()
                            .setSeconds(post.getCreateTime().getTime() / 1000))
                    .setUpdateTime(Timestamp.newBuilder()
                            .setSeconds(post.getUpdateTime().getTime() / 1000))
                    .setBest(post.getBest())
                    .setVote(post.getVote())
                    .setGroupId(post.getGroupId())
                    .setTopped(post.getTopped())
                    .setTitle(post.getTitle())
                    .setBody(post.getBody())
                    .setUid(post.getUid())
                    .setGroupName(post.getGroupName()));
        });

        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void upvotePost(PostGrpcApi.UpdatePostReq request, StreamObserver<Empty> responseObserver) {
        try {
            if (!postService.upvote(request.getPostId()))
                throw new Exception("postService.upvote returned false");
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("DB error").asException());
            return;
        }
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void downvotePost(PostGrpcApi.UpdatePostReq request, StreamObserver<Empty> responseObserver) {
        try {
            if (!postService.downvote(request.getPostId()))
                throw new Exception("postService.downvote returned false");
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("DB error").asException());
            return;
        }
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void toggleBestPost(PostGrpcApi.UpdatePostReq request, StreamObserver<Empty> responseObserver) {
        // TODO: implement privilege control
        try {
            if (!postService.toggleBest(request.getPostId()))
                throw new Exception("postService.toggleBest returned false");
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("DB error").asException());
            return;
        }
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void toggleTopPost(PostGrpcApi.UpdatePostReq request, StreamObserver<Empty> responseObserver) {
        // TODO: implement privilege control
        try {
            if (!postService.toggleTop(request.getPostId()))
                throw new Exception("postService.toggleTop returned false");
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("DB error").asException());
            return;
        }
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
