package io.xdea.xmux.forum.controller;

import io.xdea.xmux.forum.dto.ForumGrpc;
import io.xdea.xmux.forum.dto.ForumGrpcApi;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import io.xdea.xmux.forum.model.Forum;
import io.xdea.xmux.forum.service.ForumService;
import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.Date;
import java.util.List;

public abstract class ForumController extends ForumGrpc.ForumImplBase {
    protected final ForumService forumService;

    protected ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @Override
    public void createForum(ForumGrpcApi.CreateForumReq request, StreamObserver<ForumGrpcApi.CreateForumResp> responseObserver) {
        String uid = AuthInterceptor.getUid();
        Forum forum = new Forum()
                .withCreateAt(new Date())
                .withTitle(request.getTitle())
                .withDescription(request.getDescription())
                .withCreatorUid(uid);

        if (!forumService.create(forum))
            throw new RuntimeException("forumService.create returned false");

        var resp = ForumGrpcApi.CreateForumResp
                .newBuilder().setForumId(forum.getId()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void removeForum(ForumGrpcApi.RemoveForumReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.getUid();
        Forum forum = forumService.getById(request.getForumId());
        // Check if the user has privilege to remove the post
        if (forum == null) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Commmunity does not exist").asException());
            return;
        }
        String creatorUid = forum.getCreatorUid();
        if (creatorUid == null || !creatorUid.equals(uid)) {
            responseObserver.onError(Status.PERMISSION_DENIED
                    .withDescription("Not the creator of the forum").asException());
            return;
        }

        if (!forumService.hardRemove(request.getForumId()))
            throw new RuntimeException("groupService.softRemove returned false");

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getForums(ForumGrpcApi.GetForumsReq request, StreamObserver<ForumGrpcApi.GetForumsResp> responseObserver) {
        // TODO: modify service
        List<Forum> forums = forumService.get(request.getFrom(), request.getCount());
        var builder = ForumGrpcApi.GetForumsResp.newBuilder();
        forums.forEach(c -> {
            var com = ForumGrpcApi.ForumDetail.newBuilder()
                    .setId(c.getId())
                    .setCreatorUid(c.getCreatorUid())
                    .setTitle(c.getTitle())
                    .setDescription(c.getDescription());
            builder.addForums(com);
        });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

}
