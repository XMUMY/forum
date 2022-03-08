package io.xdea.xmux.forum.controller;

import io.sentry.Sentry;
import io.xdea.xmux.forum.dto.PostGrpcApi;
import io.xdea.xmux.forum.dto.SavedGrpcApi;
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

    private PostGrpcApi.PostDetails buildPostDetails(Thread thread) {
        return PostGrpcApi.PostDetails.newBuilder()
                .setId(thread.getId())
                .setCreateTime(Timestamp.newBuilder()
                        .setSeconds(thread.getCreateAt().getTime() / 1000))
                .setUpdateTime(Timestamp.newBuilder()
                        .setSeconds(thread.getUpdateAt().getTime() / 1000))
                .setBest(thread.getDigest())
                .setVote(thread.getLike())
                .setGroupId(thread.getForumId())
                .setTopped(thread.getPinned())
                .setTitle(thread.getTitle())
                .setBody(thread.getBody())
                .setUid(thread.getUid()).build();
    }

    @Override
    public void createPost(PostGrpcApi.CreatePostReq request,
                           StreamObserver<PostGrpcApi.CreatePostResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        Date nowTime = new Date();
        Thread thread = new Thread()
                .withTitle(request.getTitle())
                .withBody(request.getBody())
                .withForumId(request.getGroupId())
                .withUid(uid)
                .withCreateAt(nowTime)
                .withUpdateAt(nowTime)
                .withLike(0)
                .withDigest(false)
                .withPinned(false);
        try {
            if (!threadService.create(thread))
                throw new Exception("postService.create returned false");
        } catch (Exception e) {
            Sentry.captureException(e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("DB error").asException());
            return;
        }
        var resp = PostGrpcApi.CreatePostResp
                .newBuilder().setPostId(thread.getId()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void removePost(PostGrpcApi.UpdatePostReq request,
                           StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        try {
            Thread thread = threadService.getById(request.getPostId());
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

            if (!threadService.hardRemove(request.getPostId()))
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
    public void getPost(PostGrpcApi.GetPostReq request, StreamObserver<PostGrpcApi.GetPostResp> responseObserver) {
        List<Integer> groupIdsList = request.getGroupIdsList();
        if (groupIdsList.size() == 0)
            groupIdsList = null;
        String uid = request.getUid().equals("") ? null : request.getUid();
        PostGrpcApi.GetPostResp.Builder respBuilder = PostGrpcApi.GetPostResp.newBuilder();
        try {
            var threads = threadService.get(request.getPageNo(), request.getPageSize(), groupIdsList, uid);
            threads.forEach(thread ->
                    respBuilder.addPd(buildPostDetails(thread))
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
        Thread thread = threadService.getById(request.getPostId());
        if (thread == null) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Resource not found").asException());
            return;
        }
        responseObserver.onNext(buildPostDetails(thread));
        responseObserver.onCompleted();
    }

    @Override
    public void getSavedPost(SavedGrpcApi.GetSavedReq request, StreamObserver<PostGrpcApi.GetPostResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        List<Thread> saved = threadService.getSaved(request.getPageNo(), request.getPageSize(), uid);
        PostGrpcApi.GetPostResp.Builder builder = PostGrpcApi.GetPostResp.newBuilder();
        saved.forEach(thread -> {
            builder.addPd(buildPostDetails(thread));
        });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void upvotePost(PostGrpcApi.UpdatePostReq request, StreamObserver<Empty> responseObserver) {
        try {
            if (!threadService.upvote(request.getPostId()))
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
            if (!threadService.downvote(request.getPostId()))
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
            if (!threadService.toggleBest(request.getPostId()))
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
            if (!threadService.toggleTop(request.getPostId()))
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
