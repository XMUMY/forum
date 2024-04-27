package io.xdea.xmux.forum.controller

import com.google.protobuf.Empty
import com.google.protobuf.Timestamp
import io.grpc.stub.StreamObserver
import io.xdea.xmux.forum.dto.NotifGrpcApi
import io.xdea.xmux.forum.dto.NotifGrpcApi.GetNotifNumResp
import io.xdea.xmux.forum.dto.NotifGrpcApi.GetNotifReq
import io.xdea.xmux.forum.dto.NotifGrpcApi.GetNotifResp
import io.xdea.xmux.forum.dto.NotifGrpcApi.NotifMarkAsReadReq
import io.xdea.xmux.forum.interceptor.AuthInterceptor
import io.xdea.xmux.forum.model.NotifWithContent
import io.xdea.xmux.forum.service.ForumService
import io.xdea.xmux.forum.service.NotifService
import java.util.function.Consumer

abstract class NotifController protected constructor(
    forumService: ForumService,
    protected val notifService: NotifService
) : ForumController(forumService) {
    override fun getNotifNum(request: Empty?, responseObserver: StreamObserver<GetNotifNumResp?>) {
        val uid = AuthInterceptor.UID.get()
        val num = notifService.getUserNotifNum(uid)
        responseObserver.onNext(GetNotifNumResp.newBuilder().setNum(num.toInt()).build())
        responseObserver.onCompleted()
    }

    override fun getNotif(request: GetNotifReq, responseObserver: StreamObserver<GetNotifResp?>) {
        val uid = AuthInterceptor.UID.get()
        val notifs = notifService[uid, request.getPageNo(), request.getPageSize()]
        val respBuilder: GetNotifResp.Builder = GetNotifResp.newBuilder()
        // TODO: enrich notif content
        notifs.forEach(Consumer { notif: NotifWithContent ->
            respBuilder.addNotifs(
                NotifGrpcApi.Notif.newBuilder()
                    .setId(notif.id)
                    .setTypeValue(notif.type)
                    .setCreateTime(
                        Timestamp.newBuilder()
                            .setSeconds(notif.createAt.time / 1000)
                    )
                    .setHasRead(notif.hasRead)
                    .setSenderUid(notif.senderUid)
                    .setObjContent(notif.objContent)
                    .setRefContent(notif.refContent)
            )
        })
        responseObserver.onNext(respBuilder.build())
        responseObserver.onCompleted()
    }

    override fun notifMarkAsRead(request: NotifMarkAsReadReq, responseObserver: StreamObserver<Empty?>) {
        val uid = AuthInterceptor.UID.get()
        notifService.setAsRead(uid, request.idsList)
        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }
}
