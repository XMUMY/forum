package io.xdea.xmux.forum.controller;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.xdea.xmux.forum.dto.PostGrpcApi;
import io.xdea.xmux.forum.dto.ReplyGrpcApi;
import io.xdea.xmux.forum.dto.SavedGrpcApi;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import io.xdea.xmux.forum.service.GroupService;
import io.xdea.xmux.forum.service.PostService;
import io.xdea.xmux.forum.service.ReplyService;
import io.xdea.xmux.forum.service.SavedService;

public class SavedController extends ReplyController {
    protected final SavedService savedService;

    protected SavedController(GroupService groupService, PostService postService,
                              ReplyService replyService, SavedService savedService) {
        super(groupService, postService, replyService);
        this.savedService = savedService;
    }

    @Override
    public void savePost(SavedGrpcApi.SaveReq request, StreamObserver<SavedGrpcApi.SaveResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        try {
            if (savedService.checkPostSaved(uid, request.getRefId())) {
                responseObserver.onNext(SavedGrpcApi.SaveResp.newBuilder().setAlreadySaved(true).build());
                responseObserver.onCompleted();
                return;
            }
            if (!savedService.savePost(uid, request.getRefId()))
                throw new Exception("savedService.savePost returned false");
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("DB error").asException());
            return;
        }
        responseObserver.onNext(SavedGrpcApi.SaveResp.newBuilder().setAlreadySaved(false).build());
        responseObserver.onCompleted();
    }

    @Override
    public void saveReply(SavedGrpcApi.SaveReq request, StreamObserver<SavedGrpcApi.SaveResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        try {
            if (savedService.checkReplySaved(uid, request.getRefId())) {
                responseObserver.onNext(SavedGrpcApi.SaveResp.newBuilder().setAlreadySaved(true).build());
                responseObserver.onCompleted();
                return;
            }
            if (!savedService.saveReply(uid, request.getRefId()))
                throw new Exception("savedService.saveReply returned false");
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("DB error").asException());
            return;
        }
        responseObserver.onNext(SavedGrpcApi.SaveResp.newBuilder().setAlreadySaved(false).build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeSavedPost(SavedGrpcApi.SaveReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        try {
            if (!savedService.removeSavedPost(uid, request.getRefId()))
                throw new Exception("savedService.removeSavedPost returned false");
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
    public void removeSavedReply(SavedGrpcApi.SaveReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        try {
            if (!savedService.removeSavedReply(uid, request.getRefId()))
                throw new Exception("savedService.removeSavedReply returned false");
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
