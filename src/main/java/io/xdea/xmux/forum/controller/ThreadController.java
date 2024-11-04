package io.xdea.xmux.forum.controller;

import io.xdea.xmux.forum.dto.CommonGrpcApi;
import io.xdea.xmux.forum.dto.SavedGrpcApi;
import io.xdea.xmux.forum.dto.ThreadGrpcApi;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import io.xdea.xmux.forum.model.Forum;
import io.xdea.xmux.forum.model.Thread;
import io.xdea.xmux.forum.model.ThreadWithInfo;
import io.xdea.xmux.forum.service.ForumService;
import io.xdea.xmux.forum.service.NotifService;
import io.xdea.xmux.forum.service.ThreadService;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public abstract class ThreadController extends NotifController {

    protected final ThreadService threadService;

    protected ThreadController(ForumService forumService, NotifService notifService, ThreadService threadService) {
        super(forumService, notifService);
        this.threadService = threadService;
    }

    private ThreadGrpcApi.Thread buildThread(ThreadWithInfo thread) {
        var builder = ThreadGrpcApi.Thread.newBuilder()
                .setId(thread.getId())
                .setCreateAt(Timestamp.newBuilder()
                        .setSeconds(thread.getCreateAt().getTime() / 1000))
                .setUpdateAt(Timestamp.newBuilder()
                        .setSeconds(thread.getUpdateAt().getTime() / 1000))
                .setLastUpdate(Timestamp.newBuilder()
                        .setSeconds(thread.getLastUpdate().getTime() / 1000))
                .setDigest(thread.getDigest())
                .setLikes(thread.getLikes())
                .setPinned(thread.getPinned())
                .setTitle(thread.getTitle())
                .setUid(thread.getUid())
                .setLiked(thread.getLiked())
                .setSaved(thread.getSaved())
                .setPosts(thread.getPosts());

        var bodyCase = ThreadGrpcApi.Thread.BodyCase
                .forNumber(thread.getBodyType());
        if (bodyCase.equals(ThreadGrpcApi.Thread.BodyCase.PLAINCONTENT)) {
            var contentBuilder = CommonGrpcApi.PlainContent.newBuilder();
            contentBuilder.setContent((String) thread.getBody().get("content"));
            for (String img : (List<String>) thread.getBody().get("images")) {
                contentBuilder.addImages(CommonGrpcApi.Image.newBuilder().setUrl(img).build());
            }

            builder.setPlainContent(contentBuilder.build());

        } else if (bodyCase.equals(ThreadGrpcApi.Thread.BodyCase.MARKDOWNCONTENT)) {
            builder.setMarkdownContent(CommonGrpcApi.MarkdownContent.newBuilder()
                    .setContent((String) thread.getBody().get("content")).build());
        }

        return builder.build();
    }

    @Override
    public void createThread(ThreadGrpcApi.CreateThreadReq request, StreamObserver<ThreadGrpcApi.CreateThreadResp> responseObserver) {
        String uid = AuthInterceptor.getUid();
        Date nowTime = new Date();
        Thread thread = new Thread()
                .withTitle(request.getTitle())
                .withForumId(request.getForumId())
                .withUid(uid)
                .withCreateAt(nowTime)
                .withLastUpdate(nowTime)
                .withUpdateAt(nowTime)
                .withLikes(0)
                .withPosts(0)
                .withDigest(false)
                .withPinned(false);

        thread.setBodyType(request.getBodyCase().getNumber());
        // Store content according to type
        HashMap<String, Object> content = new HashMap<>();
        if (request.getBodyCase().equals(ThreadGrpcApi.CreateThreadReq.BodyCase.PLAINCONTENT)) {
            content.put("content", request.getPlainContent().getContent());
            // Store image URLs
            ArrayList<String> imgList = new ArrayList<>();
            for (var image : request.getPlainContent().getImagesList()) {
                imgList.add(image.getUrl());
            }
            content.put("images", imgList);

        } else if (request.getBodyCase().equals(ThreadGrpcApi.CreateThreadReq.BodyCase.MARKDOWNCONTENT)) {
            content.put("content", request.getMarkdownContent().getContent());
        }
        thread.setBody(content);

        if (!threadService.create(thread))
            throw new RuntimeException("threadService.create returned false");

        var resp = ThreadGrpcApi.CreateThreadResp
                .newBuilder().setThreadId(thread.getId()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void removeThread(ThreadGrpcApi.RemoveThreadReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.getUid();
        Thread thread = threadService.getById(request.getThreadId());
        // Check if the user has privilege to remove the thread
        if (thread == null) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Thread does not exist").asException());
            return;
        }
        String threadUid = thread.getUid();
        if (threadUid == null || !threadUid.equals(uid)) {
            responseObserver.onError(Status.PERMISSION_DENIED
                    .withDescription("Not the owner of the thread").asException());
            return;
        }

        // Check passed, remove thread and all its posts
        if (!threadService.hardRemove(request.getThreadId()))
            throw new RuntimeException("threadService.hardRemove returned false");

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void updateThread(ThreadGrpcApi.UpdateThreadReq request, StreamObserver<Empty> responseObserver) {
        if (!threadService.update(request.getId(), request.getTitle(), request.getBody()))
            throw new RuntimeException("threadService.update returned false");
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getThreads(ThreadGrpcApi.GetThreadsReq request, StreamObserver<ThreadGrpcApi.GetThreadsResp> responseObserver) {
        String uid = AuthInterceptor.getUid();
        int forumId = request.getForumId();
        final var respBuilder = ThreadGrpcApi.GetThreadsResp.newBuilder();
        var threads = threadService.get(request.getOffset(), request.getCount(), forumId, uid, request.getOrderingValue());
        threads.forEach(thread ->
                respBuilder.addThreads(buildThread(thread))
        );

        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void likeThread(ThreadGrpcApi.LikeThreadReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.getUid();
        if (request.getLike() > 0) {
            if (!threadService.upvote(request.getThreadId(), uid))
                throw new RuntimeException("threadService.upvote returned false");
        } else if (request.getLike() == 0) {
            if (!threadService.cancelVote(request.getThreadId(), uid))
                throw new RuntimeException("threadService.cancleVote returned false");
        } else {
            if (!threadService.downvote(request.getThreadId(), uid))
                throw new RuntimeException("threadService.downvote returned false");
        }
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void pinThread(ThreadGrpcApi.PinThreadReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.getUid();
        final Thread thread = threadService.getById(request.getThreadId());
        final Forum forum = forumService.getById(thread.getForumId());
        if (!forum.getCreatorUid().equals(uid)) {
            responseObserver.onError(Status.PERMISSION_DENIED.asException());
            return;
        }

        if (!threadService.togglePinned(request.getThreadId()))
            throw new RuntimeException("threadService.togglePinned returned false");
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void digestThread(ThreadGrpcApi.DigestThreadReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.getUid();
        final Thread thread = threadService.getById(request.getThreadId());
        final Forum forum = forumService.getById(thread.getForumId());
        if (!forum.getCreatorUid().equals(uid)) {
            responseObserver.onError(Status.PERMISSION_DENIED.asException());
            return;
        }

        if (!threadService.toggleDigest(request.getThreadId()))
            throw new RuntimeException("threadService.toggleDigest returned false");
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getSavedThreads(SavedGrpcApi.GetSavedThreadsReq request, StreamObserver<ThreadGrpcApi.GetThreadsResp> responseObserver) {
        String uid = AuthInterceptor.getUid();
        final var saved = threadService.getSaved(request.getOffset(), request.getCount(), uid);
        final var builder = ThreadGrpcApi.GetThreadsResp.newBuilder();
        saved.forEach(thread -> {
            builder.addThreads(buildThread(thread));
        });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
