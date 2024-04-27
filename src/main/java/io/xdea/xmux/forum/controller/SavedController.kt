package io.xdea.xmux.forum.controller

import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import io.xdea.xmux.forum.dto.SavedGrpcApi.*
import io.xdea.xmux.forum.interceptor.AuthInterceptor
import io.xdea.xmux.forum.service.*

abstract class SavedController protected constructor(
    forumService: ForumService, notifService: NotifService, threadService: ThreadService,
    postService: PostService, protected val savedService: SavedService
) : PostController(forumService, notifService, threadService, postService) {
    override fun saveThread(request: SaveThreadReq, responseObserver: StreamObserver<Empty>) {
        val uid = AuthInterceptor.UID.get()
        if (savedService.checkThreadSaved(uid, request.threadId)) {
            responseObserver.onNext(Empty.getDefaultInstance())
            responseObserver.onCompleted()
            return
        }
        if (!savedService.saveThread(
                uid,
                request.threadId
            )
        ) throw RuntimeException("savedService.saveThread returned false")

        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    override fun savePost(request: SavePostReq, responseObserver: StreamObserver<Empty>) {
        val uid = AuthInterceptor.UID.get()
        if (savedService.checkPostSaved(uid, request.postId)) {
            responseObserver.onNext(Empty.getDefaultInstance())
            responseObserver.onCompleted()
            return
        }
        if (!savedService.savePost(
                uid,
                request.postId
            )
        ) throw RuntimeException("savedService.saveThread returned false")

        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    override fun unsaveThread(request: UnsaveThreadReq, responseObserver: StreamObserver<Empty>) {
        val uid = AuthInterceptor.UID.get()
        savedService.removeSavedThread(uid, request.threadId)
        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    override fun unsavePost(request: UnsavePostReq, responseObserver: StreamObserver<Empty>) {
        val uid = AuthInterceptor.UID.get()
        savedService.removeSavedPost(uid, request.postId)
        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }
}
