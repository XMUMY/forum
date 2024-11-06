package io.xdea.xmux.forum.controller;

import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.xdea.xmux.forum.dto.CommonGrpcApi;
import io.xdea.xmux.forum.dto.PostGrpcApi;
import io.xdea.xmux.forum.dto.SavedGrpcApi;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import io.xdea.xmux.forum.model.Post;
import io.xdea.xmux.forum.model.PostWithInfo;
import io.xdea.xmux.forum.model.Thread;
import io.xdea.xmux.forum.service.AliyunGreenService;
import io.xdea.xmux.forum.service.ForumService;
import io.xdea.xmux.forum.service.NotifService;
import io.xdea.xmux.forum.service.PostService;
import io.xdea.xmux.forum.service.ThreadService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public abstract class PostController extends ThreadController {
    protected final PostService postService;

    protected PostController(ForumService forumService, NotifService notifService, ThreadService threadService, PostService postService, AliyunGreenService aliyunGreenService) {
        super(forumService, notifService, threadService, aliyunGreenService);
        this.postService = postService;
    }

    private PostGrpcApi.Post buildPost(PostWithInfo post) {
        var builder = PostGrpcApi.Post.newBuilder()
                .setId(post.getId())
                .setCreateAt(Timestamp.newBuilder()
                        .setSeconds(post.getCreateAt().getTime() / 1000))
                .setLikes(post.getLikes())
                .setPinned(post.getPinned())
                .setUid(post.getUid())
                .setParentId(post.getParentId())
                .setRefPostUid(post.getRefPostUid() == null ? "" : post.getRefPostUid())
                .setThreadId(post.getThreadId())
                .setRefPostId(post.getRefPostId())
                .setLiked(post.getLiked())
                .setSaved(post.getSaved());

        var contentCase = PostGrpcApi.Post.ContentCase
                .forNumber(post.getContentType());
        if (contentCase.equals(PostGrpcApi.Post.ContentCase.PLAINCONTENT)) {
            var contentBuilder = CommonGrpcApi.PlainContent.newBuilder();
            contentBuilder.setContent((String) post.getContent().get("content"));
            for (String img : (List<String>) post.getContent().get("images")) {
                contentBuilder.addImages(CommonGrpcApi.Image.newBuilder().setUrl(img).build());
            }

            builder.setPlainContent(contentBuilder.build());

        } else if (contentCase.equals(PostGrpcApi.Post.ContentCase.MARKDOWNCONTENT)) {
            builder.setMarkdownContent(CommonGrpcApi.MarkdownContent.newBuilder()
                    .setContent((String) post.getContent().get("content")).build());
        }
        return builder.build();
    }

    @Override
    public void getPosts(PostGrpcApi.GetPostsReq request, StreamObserver<PostGrpcApi.GetPostsResp> responseObserver) {
        String uid = AuthInterceptor.getUid();
        final var respBuilder = PostGrpcApi.GetPostsResp.newBuilder();
        var posts = postService.get(request.getOffset(), request.getCount(),
                request.getThreadId(), uid, request.getOrderingValue());
        posts.forEach(post -> respBuilder.addPosts(buildPost(post)));

        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void createPost(PostGrpcApi.CreatePostReq request,
                           StreamObserver<PostGrpcApi.CreatePostResp> responseObserver) {
        String uid = AuthInterceptor.getUid();
        // Check if the content is sensitive
        if (request.getContentCase().equals(PostGrpcApi.CreatePostReq.ContentCase.PLAINCONTENT)) {
            if (!aliyunGreenService.checkText(request.getPlainContent().getContent(), uid,
                    AliyunGreenService.ContentType.POST)) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("error.censored_content")
                    .asException());
                return;
            }
        } else if (request.getContentCase().equals(PostGrpcApi.CreatePostReq.ContentCase.MARKDOWNCONTENT)) {
            if (!aliyunGreenService.checkText(request.getMarkdownContent().getContent(), uid,
                    AliyunGreenService.ContentType.POST)) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("error.censored_content")
                    .asException());
                return;
            }
        }

        final Date nowTime = new Date();
        Post post = new Post()
                .withCreateAt(nowTime)
                .withUpdateAt(nowTime)
                .withUid(uid)
                .withPinned(false)
                .withLikes(0)
                .withThreadId(request.getThreadId())
                .withRefPostId(request.getRefPostId())
                .withParentId(request.getParentId())
                .withRefPostUid(request.getRefPostUid());

        post.setContentType(request.getContentCase().getNumber());
        // Store content according to type
        HashMap<String, Object> content = new HashMap<>();
        if (request.getContentCase().equals(PostGrpcApi.CreatePostReq.ContentCase.PLAINCONTENT)) {
            content.put("content", request.getPlainContent().getContent());
            // Store image URLs
            ArrayList<String> imgList = new ArrayList<>();
            for (var image : request.getPlainContent().getImagesList()) {
                imgList.add(image.getUrl());
            }
            content.put("images", imgList);

        } else if (request.getContentCase().equals(PostGrpcApi.CreatePostReq.ContentCase.MARKDOWNCONTENT)) {
            content.put("content", request.getMarkdownContent().getContent());
        }
        post.setContent(content);

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
        String uid = AuthInterceptor.getUid();
        if (request.getLike() > 0) {
            if (postService.upvote(request.getPostId(), uid)) {
                // Like successfully, send notif
                Post post = postService.getById(request.getPostId());
                notifService.createPostLikeNotif(post.getId(), post.getThreadId(), post.getUid(), uid);
            }

        } else if (request.getLike() == 0) {
            postService.cancelVote(request.getPostId(), uid);

        } else {
            postService.downvote(request.getPostId(), uid);
        }

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void pinPost(PostGrpcApi.PinPostReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.getUid();
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
        String uid = AuthInterceptor.getUid();
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
        if (!postService.hardRemove(request.getPostId(), post.getThreadId()))
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
        String uid = AuthInterceptor.getUid();
        final var respBuilder = PostGrpcApi.GetPostsResp.newBuilder();
        final var posts = postService.getSaved(request.getOffset(), request.getCount(), uid);
        posts.forEach(post -> respBuilder.addPosts(buildPost(post)));

        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getPostsByParent(PostGrpcApi.GetPostsByParentReq request, StreamObserver<PostGrpcApi.GetPostsResp> responseObserver) {
        String uid = AuthInterceptor.getUid();
        final var respBuilder = PostGrpcApi.GetPostsResp.newBuilder();
        final var posts = postService.getLvl2(request.getOffset(), request.getCount(), request.getParentId(), uid, request.getOrderingValue());
        posts.forEach(post -> respBuilder.addPosts(buildPost(post)));

        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }
}
