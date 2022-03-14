package io.xdea.xmux.forum.controller;

import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.xdea.xmux.forum.dto.PostGrpcApi;
import io.xdea.xmux.forum.dto.SavedGrpcApi;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import io.xdea.xmux.forum.model.Post;
import io.xdea.xmux.forum.model.Thread;
import io.xdea.xmux.forum.service.ForumService;
import io.xdea.xmux.forum.service.NotifService;
import io.xdea.xmux.forum.service.PostService;
import io.xdea.xmux.forum.service.ThreadService;

import java.util.Date;

public abstract class PostController extends ThreadController {
    protected final PostService postService;

    protected PostController(ForumService forumService, NotifService notifService, ThreadService threadService, PostService postService) {
        super(forumService, notifService, threadService);
        this.postService = postService;
    }

    private PostGrpcApi.Post buildPost(Post post) {
        return PostGrpcApi.Post.newBuilder()
                .setId(post.getId())
                .setCreateAt(Timestamp.newBuilder()
                        .setSeconds(post.getCreateAt().getTime() / 1000))
                .setLikes(post.getLikes())
                .setPinned(post.getPinned())
                .setContent(post.getContent())
                .setUid(post.getUid())
                .setParentId(post.getParentId())
                .setRefPostUid(post.getRefPostUid() == null ? "" : post.getRefPostUid())
                .setThreadId(post.getThreadId())
                .setRefPostId(post.getRefPostId()).build();
    }

    @Override
    public void getPosts(PostGrpcApi.GetPostsReq request, StreamObserver<PostGrpcApi.GetPostsResp> responseObserver) {
        final var respBuilder = PostGrpcApi.GetPostsResp.newBuilder();
        var posts = postService.get(request.getOffset(), request.getCount(),
                request.getThreadId(), request.getOrderingValue());
        posts.forEach(post -> respBuilder.addPosts(buildPost(post)));

        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void createPost(PostGrpcApi.CreatePostReq request,
                           StreamObserver<PostGrpcApi.CreatePostResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        final Date nowTime = new Date();
        Post post = new Post()
                .withCreateAt(nowTime)
                .withUpdateAt(nowTime)
                .withUid(uid)
                .withContent(request.getContent())
                .withPinned(false)
                .withLikes(0)
                .withThreadId(request.getThreadId())
                .withRefPostId(request.getRefPostId())
                .withParentId(request.getParentId())
                .withRefPostUid(request.getRefPostUid());

        // TODO: add post count
        if (!postService.create(post))
            throw new RuntimeException("postService.create returned false");

        // TODO: Implement notif

        var resp = PostGrpcApi.CreatePostResp
                .newBuilder().setPostId(post.getId()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void likePost(PostGrpcApi.LikePostReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        if (request.getLike() > 0) {
            if (!postService.upvote(request.getPostId(), uid))
                throw new RuntimeException("postService.upvote returned false");
        } else if (request.getLike() == 0) {
            if (!postService.cancelVote(request.getPostId(), uid))
                throw new RuntimeException("postService.cancleVote returned false");
        } else {
            if (!postService.downvote(request.getPostId(), uid))
                throw new RuntimeException("postService.downvote returned false");
        }
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void pinPost(PostGrpcApi.PinPostReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        final var post = postService.getById(request.getPostId());
        final Thread thread = threadService.getById(post.getThreadId());
        if (!thread.getUid().equals(uid)) {
            responseObserver.onError(Status.PERMISSION_DENIED.asException());
            return;
        }

        if (!postService.togglePinned(request.getPostId()))
            throw new RuntimeException("postService.togglePinned returned false");
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void removePost(PostGrpcApi.RemovePostReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        Post post = postService.getById(request.getPostId());
        // Check if the user has privilege to remove the post
        if (post == null) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Resource does not exist").asException());
            return;
        }
        String postUid = post.getUid();
        if (postUid == null || !postUid.equals(uid)) {
            responseObserver.onError(Status.PERMISSION_DENIED
                    .withDescription("Not the owner of the resource").asException());
            return;
        }

        // Remove post and also its children
        if (!postService.hardRemove(request.getPostId()))
            throw new RuntimeException("postService.hardRemove returned false");

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getPostsByUid(PostGrpcApi.GetPostsByUidReq request, StreamObserver<PostGrpcApi.GetPostsResp> responseObserver) {
        final var respBuilder = PostGrpcApi.GetPostsResp.newBuilder();
        var posts = postService.getUserPost(request.getOffset(), request.getCount(), request.getUid(), request.getOrderingValue());
        posts.forEach(post -> respBuilder.addPosts(buildPost(post)));

        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getSavedPosts(SavedGrpcApi.GetSavedPostsReq request, StreamObserver<PostGrpcApi.GetPostsResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        final var respBuilder = PostGrpcApi.GetPostsResp.newBuilder();
        var posts = postService.getSaved(request.getOffset(), request.getCount(), uid);
        posts.forEach(post -> respBuilder.addPosts(buildPost(post)));

        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }
}
