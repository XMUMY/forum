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
import io.xdea.xmux.forum.model.Reply;
import io.xdea.xmux.forum.service.GroupService;
import io.xdea.xmux.forum.service.NotifService;
import io.xdea.xmux.forum.service.PostService;
import io.xdea.xmux.forum.service.ReplyService;

import java.util.Date;

public abstract class ReplyController extends PostController {
    protected final ReplyService replyService;

    protected ReplyController(GroupService groupService, NotifService notifService, PostService postService, ReplyService replyService) {
        super(groupService, notifService, postService);
        this.replyService = replyService;
    }

    private ReplyGrpcApi.Reply buildReply(Reply reply) {
        return ReplyGrpcApi.Reply.newBuilder()
                .setId(reply.getId())
                .setCreateTime(Timestamp.newBuilder()
                        .setSeconds(reply.getCreateTime().getTime() / 1000))
                .setVote(reply.getVote())
                .setTopped(reply.getTopped())
                .setContent(reply.getContent())
                .setUid(reply.getUid())
                .setRefReplyId(reply.getRefReplyId())
                .setRefUid(reply.getRefUid() == null ? "" : reply.getRefUid())
                .setRefPostId(reply.getRefPostId()).build();
    }

    @Override
    public void createReply(ReplyGrpcApi.CreateReplyReq request, StreamObserver<ReplyGrpcApi.CreateReplyResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        String refUid = null;
        // When the reply is to a post, the reply id == -1, post id != -1,
        // When the reply is to a reply, both reply and post id != -1.
        if (request.getRefReplyId() != -1) {
            Reply refReply = replyService.getById(request.getRefReplyId());
            if (refReply == null) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Resource not exist").asException());
                return;
            }
            refUid = refReply.getUid();
        } else {
            Post refPost = postService.getById(request.getRefPostId());
            if (refPost == null) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Resource not exist").asException());
                return;
            }
            refUid = refPost.getUid();
        }

        Reply reply = new Reply()
                .withCreateTime(new Date())
                .withUid(uid)
                .withContent(request.getContent())
                .withTopped(false)
                .withVote(0)
                .withRefReplyId(request.getRefReplyId())
                .withRefPostId(request.getRefPostId())
                .withRefUid(refUid);

        if (!replyService.create(reply))
            throw new RuntimeException("replyService.create returned false");
        if (!postService.renewUpdateTime(request.getRefPostId()))
            throw new RuntimeException("postService.renewUpdateTime returned false");

        // TODO: send message to NATS
        Notif notif = new Notif()
                .withHasRead(false)
                .withCreateTime(new Date())
                .withObjId(reply.getId())
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
                .newBuilder().setReplyId(reply.getId()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void getReply(ReplyGrpcApi.GetReplyReq request, StreamObserver<ReplyGrpcApi.GetReplyResp> responseObserver) {
        ReplyGrpcApi.GetReplyResp.Builder respBuilder = ReplyGrpcApi.GetReplyResp.newBuilder();
        try {
            var replies = replyService.get(request.getPageNo(), request.getPageSize(),
                    request.getRefPostId(), null, request.getSortValue());
            replies.forEach(reply -> respBuilder.addReplies(buildReply(reply)));
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
            var replies = replyService.getUserReply(request.getPageNo(), request.getPageSize(), request.getUid());
            replies.forEach(reply -> respBuilder.addReplies(buildReply(reply)));
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
            var replies = replyService.getSaved(request.getPageNo(), request.getPageSize(), uid);
            replies.forEach(reply -> respBuilder.addReplies(buildReply(reply)));
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
            Reply reply = replyService.getById(request.getReplyId());
            if (reply == null) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Resource not exist").asException());
                return;
            }
            responseObserver.onNext(buildReply(reply));
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
            Reply reply = replyService.getById(request.getReplyId());
            // Check if the user has privilege to remove the post
            if (reply == null) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Resource does not exist").asException());
                return;
            }
            String replyUid = reply.getUid();
            if (replyUid == null || !replyUid.equals(uid)) {
                responseObserver.onError(Status.PERMISSION_DENIED
                        .withDescription("Not the owner of the resource").asException());
                return;
            }

            // Soft delete
            if (!replyService.softRemove(request.getReplyId()))
                throw new Exception("replyService.softRemove returned false");
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
