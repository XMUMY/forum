package io.xdea.xmux.forum.controller;

import io.xdea.xmux.forum.dto.SavedGrpcApi;
import io.xdea.xmux.forum.dto.ThreadGrpcApi;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import io.xdea.xmux.forum.model.Thread;
import io.xdea.xmux.forum.service.ForumService;
import io.xdea.xmux.forum.service.NotifService;
import io.xdea.xmux.forum.service.ThreadService;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.Date;
import java.util.List;

public abstract class ThreadController extends NotifController {

    protected final ThreadService threadService;

    protected ThreadController(ForumService forumService, NotifService notifService, ThreadService threadService) {
        super(forumService, notifService);
        this.threadService = threadService;
    }

    private ThreadGrpcApi.Thread buildThread(Thread thread) {
        return ThreadGrpcApi.Thread.newBuilder()
                .setId(thread.getId())
                .setCreateAt(Timestamp.newBuilder()
                        .setSeconds(thread.getCreateAt().getTime() / 1000))
                .setUpdateAt(Timestamp.newBuilder()
                        .setSeconds(thread.getUpdateAt().getTime() / 1000))
                .setLastUpdate(Timestamp.newBuilder()
                        // TODO: update attribute
                        .setSeconds(thread.getUpdateAt().getTime() / 1000))
                .setDigest(thread.getDigest())
                .setLikes(thread.getLike())
                .setPinned(thread.getPinned())
                .setTitle(thread.getTitle())
                .setBody(thread.getBody())
                .setUid(thread.getUid()).build();
    }

    @Override
    public void createThread(ThreadGrpcApi.CreateThreadReq request, StreamObserver<ThreadGrpcApi.CreateThreadResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        Date nowTime = new Date();
        Thread thread = new Thread()
                .withTitle(request.getTitle())
                .withBody(request.getBody())
                .withForumId(request.getForumId())
                .withUid(uid)
                .withCreateAt(nowTime)
                // TODO: add lastUpdate
                .withUpdateAt(nowTime)
                .withLike(0)
                .withDigest(false)
                .withPinned(false);

        if (!threadService.create(thread))
            throw new RuntimeException("threadService.create returned false");

        var resp = ThreadGrpcApi.CreateThreadResp
                .newBuilder().setThreadId(thread.getId()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void removeThread(ThreadGrpcApi.RemoveThreadReq request, StreamObserver<Empty> responseObserver) {
        // TODO: remove posts under it
        String uid = AuthInterceptor.UID.get();
        Thread thread = threadService.getById(request.getThreadId());
        // Check if the user has privilege to remove the thread
        if (thread == null) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Post does not exist").asException());
            return;
        }
        String threadUid = thread.getUid();
        if (threadUid == null || !threadUid.equals(uid)) {
            responseObserver.onError(Status.PERMISSION_DENIED
                    .withDescription("Not the owner of the thread").asException());
            return;
        }

        if (!threadService.hardRemove(request.getThreadId()))
            throw new RuntimeException("threadService.hardRemove returned false");

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void updateThread(ThreadGrpcApi.UpdateThreadReq request, StreamObserver<Empty> responseObserver) {
        // TODO: impl
    }

    @Override
    public void getThreads(ThreadGrpcApi.GetThreadsReq request, StreamObserver<ThreadGrpcApi.GetThreadsResp> responseObserver) {
        int forumId = request.getForumId();
        final var respBuilder = ThreadGrpcApi.GetThreadsResp.newBuilder();
        var threads = threadService.get(request.getCursor(), request.getCount(), forumId);
        threads.forEach(thread ->
                respBuilder.addThreads(buildThread(thread))
        );

        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void likeThread(ThreadGrpcApi.LikeThreadReq request, StreamObserver<Empty> responseObserver) {
        // TODO: Fix
        if (!threadService.upvote(request.getThreadId()))
            throw new RuntimeException("postService.upvote returned false");
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void pinThread(ThreadGrpcApi.PinThreadReq request, StreamObserver<Empty> responseObserver) {
        // TODO: implement privilege control
        if (!threadService.togglePinned(request.getThreadId()))
            throw new RuntimeException("postService.toggleTop returned false");
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void digestThread(ThreadGrpcApi.DigestThreadReq request, StreamObserver<Empty> responseObserver) {
        // TODO: implement privilege control
        if (!threadService.toggleDigest(request.getThreadId()))
            throw new RuntimeException("threadService.toggleDigest returned false");
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getSavedThreads(SavedGrpcApi.GetSavedThreadsReq request, StreamObserver<ThreadGrpcApi.GetThreadsResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        // TODO: change paging
        List<Thread> saved = threadService.getSaved(request.getCursor(), request.getCount(), uid);
        final var builder = ThreadGrpcApi.GetThreadsResp.newBuilder();
        saved.forEach(thread -> {
            builder.addThreads(buildThread(thread));
        });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
