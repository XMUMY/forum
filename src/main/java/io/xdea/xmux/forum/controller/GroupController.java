package io.xdea.xmux.forum.controller;

import io.sentry.Sentry;
import io.xdea.xmux.forum.dto.ForumGrpc;
import io.xdea.xmux.forum.dto.GroupGrpcApi;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import io.xdea.xmux.forum.model.Group;
import io.xdea.xmux.forum.service.GroupService;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.Date;
import java.util.List;

public abstract class GroupController extends ForumGrpc.ForumImplBase {
    protected final GroupService groupService;

    protected GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public void createGroup(GroupGrpcApi.CreateGroupReq request, StreamObserver<GroupGrpcApi.CreateGroupResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        Group group = new Group()
                .withCreateTime(new Date())
                .withTitle(request.getTitle())
                .withDescription(request.getDescription())
                .withCreatorUid(uid);
        try {
            if (!groupService.create(group))
                throw new Exception("groupService.create returned false");
        } catch (Exception e) {
            Sentry.captureException(e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("DB error").asException());
            return;
        }
        var resp = GroupGrpcApi.CreateGroupResp
                .newBuilder().setGroupId(group.getId()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void removeGroup(GroupGrpcApi.GroupIdMsg request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        try {
            Group group = groupService.getById(request.getGroupId());
            // Check if the user has privilege to remove the post
            if (group == null) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Commmunity does not exist").asException());
                return;
            }
            String creatorUid = group.getCreatorUid();
            if (creatorUid == null || !creatorUid.equals(uid)) {
                responseObserver.onError(Status.PERMISSION_DENIED
                        .withDescription("Not the creator of the group").asException());
                return;
            }

            // Soft delete
            if (!groupService.softRemove(request.getGroupId()))
                throw new Exception("groupService.softRemove returned false");
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
    public void joinGroup(GroupGrpcApi.MembershipMsg request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();

        try {
            if (!groupService.checkMembershipExist(uid, request.getGroupId())) {
                if (!groupService.createMembership(uid, request.getGroupId()))
                    throw new Exception("groupService.createMembership returned false");
            }
        } catch (Exception e) {
            Sentry.captureException(e);
            responseObserver.onError(Status.INTERNAL.withDescription("DB error").asException());
            return;
        }
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void leaveGroup(GroupGrpcApi.MembershipMsg request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        try {
            if (groupService.checkMembershipExist(uid, request.getGroupId())) {
                if (!groupService.removeMembership(uid, request.getGroupId()))
                    throw new Exception("groupService.removeMembership returned false");
            }
        } catch (Exception e) {
            Sentry.captureException(e);
            responseObserver.onError(Status.INTERNAL.withDescription("DB error").asException());
            return;
        }
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getUserGroups(Empty request, StreamObserver<GroupGrpcApi.GetUserGroupsResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        var respBuilder = GroupGrpcApi.GetUserGroupsResp.newBuilder();
        try {
            groupService.getMemberGroup(uid).forEach(
                    group -> respBuilder.addGroups(
                            GroupGrpcApi.GroupBrief.newBuilder()
                                    .setId(group.getId())
                                    .setTitle(group.getTitle())
                                    .build())
            );
        } catch (Exception e) {
            Sentry.captureException(e);
            responseObserver.onError(Status.INTERNAL.withDescription("DB error").asException());
            return;
        }
        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getGroups(GroupGrpcApi.GetGroupsReq request, StreamObserver<GroupGrpcApi.GetGroupsResp> responseObserver) {
        try {
            List<Group> groups = groupService.get(request.getPageNo(), request.getPageSize());
            var builder = GroupGrpcApi.GetGroupsResp.newBuilder();
            groups.forEach(c -> {
                var com = GroupGrpcApi.Group.newBuilder()
                        .setId(c.getId())
                        .setCreateTime(Timestamp.newBuilder()
                                .setSeconds(c.getCreateTime().getTime() / 1000).build())
                        .setCreatorUid(c.getCreatorUid())
                        .setTitle(c.getTitle())
                        .setDescription(c.getDescription());
                builder.addGroups(com);
            });
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            Sentry.captureException(e);
            throw e;
        }
    }
}
