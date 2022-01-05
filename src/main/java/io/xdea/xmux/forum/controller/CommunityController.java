package io.xdea.xmux.forum.controller;

import io.xdea.xmux.forum.dto.CommunityGrpcApi;
import io.xdea.xmux.forum.dto.CommunityServiceGrpc;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import io.xdea.xmux.forum.model.Community;
import io.xdea.xmux.forum.service.CommunityService;
import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@GrpcService
public class CommunityController extends CommunityServiceGrpc.CommunityServiceImplBase {
    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @Override
    public void createCommunity(CommunityGrpcApi.CreateCommunityReq request, StreamObserver<CommunityGrpcApi.CreateCommunityResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        Community community = new Community()
                .withCreateTime(new Date())
                .withTitle(request.getTitle())
                .withDescription(request.getDescription())
                .withCreatorUid(uid);
        try {
            if (!communityService.create(community))
                throw new Exception("communityService.create returned false");
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("DB error").asException());
            return;
        }
        var resp = CommunityGrpcApi.CreateCommunityResp
                .newBuilder().setCommunityId(community.getId()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void removeCommunity(CommunityGrpcApi.CommunityIdMsg request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        Community community = communityService.getById(request.getCommunityId());
        // Check if the user has privilege to remove the post
        if (community == null) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Commmunity does not exist").asException());
            return;
        }
        String creatorUid = community.getCreatorUid();
        if (creatorUid == null || !creatorUid.equals(uid)) {
            responseObserver.onError(Status.PERMISSION_DENIED
                    .withDescription("Not the creator of the community").asException());
            return;
        }

        // Soft delete
        try {
            if (!communityService.softRemove(request.getCommunityId()))
                throw new Exception("communityService.softRemove returned false");
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
    public void joinCommunity(CommunityGrpcApi.MembershipMsg request, StreamObserver<Empty> responseObserver) {
        if (!communityService.checkMembershipExist(request.getUid(), request.getCommunityId())) {
            try {
                if (!communityService.createMembership(request.getUid(), request.getCommunityId()))
                    throw new Exception("communityService.createMembership returned false");
            } catch (Exception e) {
                e.printStackTrace();
                responseObserver.onError(Status.INTERNAL.withDescription("DB error").asException());
                return;
            }
        }
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void leaveCommunity(CommunityGrpcApi.MembershipMsg request, StreamObserver<Empty> responseObserver) {
        if (communityService.checkMembershipExist(request.getUid(), request.getCommunityId())) {
            try {
                if (!communityService.removeMembership(request.getUid(), request.getCommunityId()))
                    throw new Exception("communityService.removeMembership returned false");
            } catch (Exception e) {
                e.printStackTrace();
                responseObserver.onError(Status.INTERNAL.withDescription("DB error").asException());
                return;
            }
        }
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getCommunities(CommunityGrpcApi.GetCommunitiesReq request, StreamObserver<CommunityGrpcApi.GetCommunitiesResp> responseObserver) {
        List<Community> communities = communityService.get(request.getPageNo(), request.getPageSize());
        var builder = CommunityGrpcApi.GetCommunitiesResp.newBuilder();
        communities.forEach(c -> {
            var com = CommunityGrpcApi.Community.newBuilder()
                    .setId(c.getId())
                    .setCreateTime(Timestamp.newBuilder()
                            .setSeconds(c.getCreateTime().getTime() / 1000).build())
                    .setCreatorUid(c.getCreatorUid())
                    .setTitle(c.getTitle())
                    .setDescription(c.getDescription());
            builder.addCommunities(com);
        });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
