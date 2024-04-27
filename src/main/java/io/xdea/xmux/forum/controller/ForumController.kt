package io.xdea.xmux.forum.controller

import com.google.protobuf.Empty
import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.xdea.xmux.forum.dto.ForumGrpc.ForumImplBase
import io.xdea.xmux.forum.dto.ForumGrpcApi.*
import io.xdea.xmux.forum.interceptor.AuthInterceptor
import io.xdea.xmux.forum.model.Forum
import io.xdea.xmux.forum.service.ForumService
import java.util.*
import java.util.function.Consumer

abstract class ForumController protected constructor(protected val forumService: ForumService) : ForumImplBase() {
    override fun createForum(request: CreateForumReq, responseObserver: StreamObserver<CreateForumResp>) {
        val uid = AuthInterceptor.UID.get()
        val forum = Forum()
            .withCreateAt(Date())
            .withTitle(request.title)
            .withDescription(request.description)
            .withCreatorUid(uid)

        if (!forumService.create(forum)) throw RuntimeException("forumService.create returned false")

        val resp = CreateForumResp
            .newBuilder().setForumId(forum.id).build()
        responseObserver.onNext(resp)
        responseObserver.onCompleted()
    }

    override fun removeForum(request: RemoveForumReq, responseObserver: StreamObserver<Empty>) {
        val uid = AuthInterceptor.UID.get()
        val forum = forumService.getById(request.forumId)
        // Check if the user has privilege to remove the post
        if (forum == null) {
            responseObserver.onError(
                Status.INVALID_ARGUMENT
                    .withDescription("Commmunity does not exist").asException()
            )
            return
        }
        val creatorUid = forum.creatorUid
        if (creatorUid == null || creatorUid != uid) {
            responseObserver.onError(
                Status.PERMISSION_DENIED
                    .withDescription("Not the creator of the forum").asException()
            )
            return
        }

        if (!forumService.hardRemove(request.forumId)) throw RuntimeException("groupService.softRemove returned false")

        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    override fun getForums(request: GetForumsReq, responseObserver: StreamObserver<GetForumsResp>) {
        // TODO: modify service
        val forums = forumService[request.from, request.count]
        val builder = GetForumsResp.newBuilder()
        forums.forEach(Consumer { c: Forum ->
            val com = ForumDetail.newBuilder()
                .setId(c.id)
                .setCreatorUid(c.creatorUid)
                .setTitle(c.title)
                .setDescription(c.description)
            builder.addForums(com)
        })
        responseObserver.onNext(builder.build())
        responseObserver.onCompleted()
    }
}
