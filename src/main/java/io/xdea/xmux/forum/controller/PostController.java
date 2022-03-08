package io.xdea.xmux.forum.controller;

import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.sentry.Sentry;
import io.xdea.xmux.forum.dto.NotifGrpcApi;
import io.xdea.xmux.forum.dto.ReplyGrpcApi;
import io.xdea.xmux.forum.dto.SavedGrpcApi;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import io.xdea.xmux.forum.model.Notif;
import io.xdea.xmux.forum.model.Post;
import io.xdea.xmux.forum.model.Thread;
import io.xdea.xmux.forum.service.ForumService;
import io.xdea.xmux.forum.service.NotifService;
import io.xdea.xmux.forum.service.ThreadService;
import io.xdea.xmux.forum.service.PostService;

import java.util.Date;
import java.util.Objects;

public abstract class PostController extends ThreadController {
    protected final PostService postService;

    protected PostController(ForumService forumService, NotifService notifService, ThreadService threadService, PostService postService) {
        super(forumService, notifService, threadService);
        this.postService = postService;
    }

    private ReplyGrpcApi.Reply buildReply(Post post) {
        return ReplyGrpcApi.Reply.newBuilder()
                .setId(post.getId())
                .setCreateTime(Timestamp.newBuilder()
                        .setSeconds(post.getCreateAt().getTime() / 1000))
                .setVote(post.getLike())
                .setTopped(post.getPinned())
                .setContent(post.getContent())
                .setUid(post.getUid())
                .setRefReplyId(post.getParentId())
                .setRefUid(post.getRefPostUid() == null ? "" : post.getRefPostUid())
                .setRefPostId(post.getThreadId()).build();
    }

    @Override
    public void createReply(ReplyGrpcApi.CreateReplyReq request, StreamObserver<ReplyGrpcApi.CreateReplyResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        String refUid = null;
        Integer refPostId = null;
        // When the post is to a thread, the post id == -1, thread id != -1,
        // When the post is to a post, both post and thread id != -1.
        if (request.getRefReplyId() != -1) {
            Post refPost = postService.getById(request.getRefReplyId());
            if (refPost == null) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Resource not exist").asException());
                return;
            }
            refUid = refPost.getUid();
            if (!refPost.getParentId().equals(-1)) {
                // Means new post is lvl 1
                // If parentId of refPost is not -1, means new post is lvl 2
                refPostId = refPost.getRefPostId();
            }
        } else {
            // Referring to a thread, lvl 0
            Thread refThread = threadService.getById(request.getRefPostId());
            if (refThread == null) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Resource not exist").asException());
                return;
            }
            refUid = refThread.getUid();
        }

        Post post = new Post()
                .withCreateAt(new Date())
                .withUid(uid)
                .withContent(request.getContent())
                .withPinned(false)
                .withLike(0)
                .withThreadId(request.getRefPostId())
                // differenciate lvl 1 & 2
                .withRefPostId(Objects.requireNonNullElseGet(refPostId, request::getRefReplyId))
                .withParentId(request.getRefReplyId())
                .withRefPostUid(refUid);

        if (!postService.create(post))
            throw new RuntimeException("postService.create returned false");
        if (!threadService.renewUpdateTime(request.getRefPostId()))
            throw new RuntimeException("threadService.renewUpdateTime returned false");

        // TODO: send message to NATS
        Notif notif = new Notif()
                .withHasRead(false)
                .withCreateTime(new Date())
                .withObjId(post.getId())
                .withUid(refUid)
                .withSenderUid(uid);

        if (request.getRefReplyId() != -1) {
            notif.setRefId(request.getRefReplyId());
            notif.setType(NotifGrpcApi.NotifType.REPLY_REPLY_VALUE);
        } else {
            notif.setRefId(request.getRefPostId());
            notif.setType(NotifGrpcApi.NotifType.POST_REPLY_VALUE);
        }

        if (!notifService.createNotif(notif))
            throw new RuntimeException("notifService.createNotif returned false");

        var resp = ReplyGrpcApi.CreateReplyResp
                .newBuilder().setReplyId(post.getId()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void getReply(ReplyGrpcApi.GetReplyReq request, StreamObserver<ReplyGrpcApi.GetReplyResp> responseObserver) {
        ReplyGrpcApi.GetReplyResp.Builder respBuilder = ReplyGrpcApi.GetReplyResp.newBuilder();
        try {
            var posts = postService.get(request.getPageNo(), request.getPageSize(),
                    request.getRefPostId(), request.getSortValue());
            posts.forEach(post -> respBuilder.addReplies(buildReply(post)));
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
    public void getUserReply(ReplyGrpcApi.GetUserReplyReq request, StreamObserver<ReplyGrpcApi.GetReplyResp> responseObserver) {
        ReplyGrpcApi.GetReplyResp.Builder respBuilder = ReplyGrpcApi.GetReplyResp.newBuilder();
        try {
            var posts = postService.getUserPost(request.getPageNo(), request.getPageSize(), request.getUid());
            posts.forEach(post -> respBuilder.addReplies(buildReply(post)));
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
    public void getSavedReply(SavedGrpcApi.GetSavedReq request, StreamObserver<ReplyGrpcApi.GetReplyResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        ReplyGrpcApi.GetReplyResp.Builder respBuilder = ReplyGrpcApi.GetReplyResp.newBuilder();
        try {
            var posts = postService.getSaved(request.getPageNo(), request.getPageSize(), uid);
            posts.forEach(post -> respBuilder.addReplies(buildReply(post)));
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
    public void getReplyById(ReplyGrpcApi.GetReplyByIdReq request, StreamObserver<ReplyGrpcApi.Reply> responseObserver) {
        try {
            Post post = postService.getById(request.getReplyId());
            if (post == null) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Resource not exist").asException());
                return;
            }
            responseObserver.onNext(buildReply(post));
            responseObserver.onCompleted();
        } catch (Exception e) {
            Sentry.captureException(e);
            throw e;
        }
    }

    @Override
    public void removeReply(ReplyGrpcApi.UpdateReplyReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        try {
            Post post = postService.getById(request.getReplyId());
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

            if (!postService.hardRemove(request.getReplyId()))
                throw new Exception("postService.hardRemove returned false");
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
    public void upvoteReply(ReplyGrpcApi.UpdateReplyReq request, StreamObserver<Empty> responseObserver) {
        // TODO: Implement this (with record)
    }

    @Override
    public void downvoteReply(ReplyGrpcApi.UpdateReplyReq request, StreamObserver<Empty> responseObserver) {
    }
}
