package io.xdea.xmux.forum.controller;

import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.xdea.xmux.forum.dto.ReplyGrpcApi;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import io.xdea.xmux.forum.model.Reply;
import io.xdea.xmux.forum.service.GroupService;
import io.xdea.xmux.forum.service.PostService;
import io.xdea.xmux.forum.service.ReplyService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class ReplyController extends PostController {
    private final ReplyService replyService;

    protected ReplyController(GroupService groupService, PostService postService, ReplyService replyService) {
        super(groupService, postService);
        this.replyService = replyService;
    }

    @Override
    public void createReply(ReplyGrpcApi.CreateReplyReq request, StreamObserver<ReplyGrpcApi.CreateReplyResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        Reply reply = new Reply()
                .withCreateTime(new Date())
                .withUid(uid)
                .withContent(request.getContent())
                .withTopped(false)
                .withVote(0);
        if (request.getRefTypeValue() == 0) {
            reply.withRefReplyId(-1)
                    .withRefPostId(request.getRefId());
        } else if (request.getRefTypeValue() == 1) {
            reply.withRefReplyId(request.getRefId())
                    .withRefPostId(-1);
        }
        try {
            if (!replyService.create(reply))
                throw new Exception("replyService.create returned false");
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("DB error").asException());
            return;
        }
        var resp = ReplyGrpcApi.CreateReplyResp
                .newBuilder().setReplyId(reply.getId()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void getReply(ReplyGrpcApi.GetReplyReq request, StreamObserver<ReplyGrpcApi.GetReplyResp> responseObserver) {
        List<Reply> replies = new ArrayList<>();
        if (request.getRefTypeValue() == 0) {
            replies = replyService.get(request.getPageNo(), request.getPageSize(),
                    request.getRefId(), null, request.getSortValue() == 1);
        } else if (request.getRefTypeValue() == 1) {
            replies = replyService.get(request.getPageNo(), request.getPageSize(),
                    null, request.getRefId(), request.getSortValue() == 1);
        }
        ReplyGrpcApi.GetReplyResp.Builder respBuilder = ReplyGrpcApi.GetReplyResp.newBuilder();
        replies.forEach(reply ->
                respBuilder.addReplies(ReplyGrpcApi.Reply.newBuilder()
                        .setId(reply.getId())
                        .setCreateTime(Timestamp.newBuilder()
                                .setSeconds(reply.getCreateTime().getTime() / 1000))
                        .setVote(reply.getVote())
                        .setTopped(reply.getTopped())
                        .setContent(reply.getContent())
                        .setUid(reply.getUid()))
        );

        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeReply(ReplyGrpcApi.UpdateReplyReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();
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
        try {
            if (!replyService.softRemove(request.getReplyId()))
                throw new Exception("replyService.softRemove returned false");
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
    public void upvoteReply(ReplyGrpcApi.UpdateReplyReq request, StreamObserver<Empty> responseObserver) {
        // TODO: Implement this (with record)
    }

    @Override
    public void downvoteReply(ReplyGrpcApi.UpdateReplyReq request, StreamObserver<Empty> responseObserver) {
    }
}