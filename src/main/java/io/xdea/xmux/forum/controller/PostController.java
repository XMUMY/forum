package io.xdea.xmux.forum.controller;

import io.sentry.Sentry;
import io.xdea.xmux.forum.dto.PostGrpcApi;
import io.xdea.xmux.forum.dto.SavedGrpcApi;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import io.xdea.xmux.forum.model.Post;
import io.xdea.xmux.forum.service.GroupService;
import io.xdea.xmux.forum.service.NotifService;
import io.xdea.xmux.forum.service.PostService;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.Date;
import java.util.List;

public abstract class PostController extends NotifController {

    protected final PostService postService;

    protected PostController(GroupService groupService, NotifService notifService, PostService postService) {
        super(groupService, notifService);
        this.postService = postService;
    }

    private PostGrpcApi.PostDetails buildPostDetails(Post post) {
        return PostGrpcApi.PostDetails.newBuilder()
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
                .setUid(post.getUid()).build();
    }

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
            Sentry.captureException(e);
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
        try {
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
            if (!postService.softRemove(request.getPostId()))
                throw new Exception("postService.softRemove returned false");
        } catch (Exception e) {
            Sentry.captureException(e);
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
        String uid = request.getUid().equals("") ? null : request.getUid();
        PostGrpcApi.GetPostResp.Builder respBuilder = PostGrpcApi.GetPostResp.newBuilder();
        try {
            var posts = postService.get(request.getPageNo(), request.getPageSize(), groupIdsList, uid);
            // ASK: is there better way to avoid copying?
            posts.forEach(post ->
                    respBuilder.addPd(buildPostDetails(post))
            );
        } catch (Exception e) {
            Sentry.captureException(e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("DB error").asException());
            return;
        }

        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getPostById(PostGrpcApi.GetPostByIdReq request, StreamObserver<PostGrpcApi.PostDetails> responseObserver) {
        Post post = postService.getById(request.getPostId());
        if (post == null) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Resource not found").asException());
            return;
        }
        responseObserver.onNext(buildPostDetails(post));
        responseObserver.onCompleted();
    }

    @Override
    public void getSavedPost(SavedGrpcApi.GetSavedReq request, StreamObserver<PostGrpcApi.GetPostResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        List<Post> saved = postService.getSaved(request.getPageNo(), request.getPageSize(), uid);
        PostGrpcApi.GetPostResp.Builder builder = PostGrpcApi.GetPostResp.newBuilder();
        saved.forEach(post -> {
            builder.addPd(buildPostDetails(post));
        });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void upvotePost(PostGrpcApi.UpdatePostReq request, StreamObserver<Empty> responseObserver) {
        try {
            if (!postService.upvote(request.getPostId()))
                throw new Exception("postService.upvote returned false");
        } catch (Exception e) {
            Sentry.captureException(e);
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
            Sentry.captureException(e);
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
            Sentry.captureException(e);
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
            Sentry.captureException(e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("DB error").asException());
            return;
        }
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
