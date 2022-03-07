package io.xdea.xmux.forum.controller;

import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import io.xdea.xmux.forum.dto.NotifGrpcApi;
import io.xdea.xmux.forum.interceptor.AuthInterceptor;
import io.xdea.xmux.forum.service.*;

public abstract class NotifController extends ForumController {
    final protected NotifService notifService;

    protected NotifController(ForumService forumService, NotifService notifService) {
        super(forumService);
        this.notifService = notifService;
    }

    @Override
    public void getNotifNum(Empty request, StreamObserver<NotifGrpcApi.GetNotifNumResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        Long num = notifService.getUserNotifNum(uid);
        responseObserver.onNext(NotifGrpcApi.GetNotifNumResp.newBuilder().setNum(num.intValue()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getNotif(NotifGrpcApi.GetNotifReq request, StreamObserver<NotifGrpcApi.GetNotifResp> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        var notifs = notifService.get(uid, request.getPageNo(), request.getPageSize());
        var respBuilder = NotifGrpcApi.GetNotifResp.newBuilder();
        notifs.forEach(notif -> respBuilder.addNotifs(NotifGrpcApi.Notif.newBuilder()
                .setId(notif.getId())
                .setTypeValue(notif.getType())
                .setCreateTime(Timestamp.newBuilder()
                        .setSeconds(notif.getCreateTime().getTime() / 1000))
                .setHasRead(notif.getHasRead())
                .setRefId(notif.getRefId())
                .setObjId(notif.getObjId())
                .setSenderUid(notif.getSenderUid())
                .setObjContent(notif.getObjContent())
                .setRefContent(notif.getRefContent())));
        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void notifMarkAsRead(NotifGrpcApi.NotifMarkAsReadReq request, StreamObserver<Empty> responseObserver) {
        String uid = AuthInterceptor.UID.get();
        int numUpdated = notifService.setAsRead(uid, request.getIdsList());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
