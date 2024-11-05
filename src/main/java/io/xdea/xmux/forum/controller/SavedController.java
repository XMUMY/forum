package io.xdea.xmux.forum.controller;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import io.xdea.xmux.forum.dto.SavedGrpcApi;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import io.xdea.xmux.forum.service.*;

public abstract class SavedController extends PostController {
    protected final SavedService savedService;

    protected SavedController(ForumService forumService, NotifService notifService, ThreadService threadService,
                              PostService postService, SavedService savedService, AliyunGreenService aliyunGreenService) {
        super(forumService, notifService, threadService, postService, aliyunGreenService);
        this.savedService = savedService;
    }

    @Override
    public void saveThread(SavedGrpcApi.SaveThreadReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.getUid();
        if (savedService.checkThreadSaved(uid, request.getThreadId())) {
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
            return;
        }
        if (!savedService.saveThread(uid, request.getThreadId()))
            throw new RuntimeException("savedService.saveThread returned false");

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void savePost(SavedGrpcApi.SavePostReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.getUid();
        if (savedService.checkPostSaved(uid, request.getPostId())) {
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
            return;
        }
        if (!savedService.savePost(uid, request.getPostId()))
            throw new RuntimeException("savedService.saveThread returned false");

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void unsaveThread(SavedGrpcApi.UnsaveThreadReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.getUid();
        savedService.removeSavedThread(uid, request.getThreadId());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void unsavePost(SavedGrpcApi.UnsavePostReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.getUid();
        savedService.removeSavedPost(uid, request.getPostId());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
