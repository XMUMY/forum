package io.xdea.xmux.forum.controller

import com.google.protobuf.Empty
import com.google.protobuf.Timestamp
import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.xdea.xmux.forum.dto.CommonGrpcApi
import io.xdea.xmux.forum.dto.CommonGrpcApi.PlainContent
import io.xdea.xmux.forum.dto.SavedGrpcApi.GetSavedThreadsReq
import io.xdea.xmux.forum.dto.ThreadGrpcApi
import io.xdea.xmux.forum.dto.ThreadGrpcApi.*
import io.xdea.xmux.forum.interceptor.AuthInterceptor
import io.xdea.xmux.forum.model.Thread
import io.xdea.xmux.forum.model.ThreadWithInfo
import io.xdea.xmux.forum.service.ForumService
import io.xdea.xmux.forum.service.NotifService
import io.xdea.xmux.forum.service.ThreadService
import java.util.*
import java.util.function.Consumer

abstract class ThreadController protected constructor(
    forumService: ForumService,
    notifService: NotifService,
    protected val threadService: ThreadService
) : NotifController(forumService, notifService) {
    private fun buildThread(thread: ThreadWithInfo): ThreadGrpcApi.Thread {
        val builder = ThreadGrpcApi.Thread.newBuilder()
            .setId(thread.id)
            .setCreateAt(
                Timestamp.newBuilder()
                    .setSeconds(thread.createAt.time / 1000)
            )
            .setUpdateAt(
                Timestamp.newBuilder()
                    .setSeconds(thread.updateAt.time / 1000)
            )
            .setLastUpdate(
                Timestamp.newBuilder()
                    .setSeconds(thread.lastUpdate.time / 1000)
            )
            .setDigest(thread.digest)
            .setLikes(thread.likes)
            .setPinned(thread.pinned)
            .setTitle(thread.title)
            .setUid(thread.uid)
            .setLiked(thread.liked)
            .setSaved(thread.saved)
            .setPosts(thread.posts)

        val bodyCase = ThreadGrpcApi.Thread.BodyCase
            .forNumber(thread.bodyType)
        if (bodyCase == ThreadGrpcApi.Thread.BodyCase.PLAINCONTENT) {
            val contentBuilder = PlainContent.newBuilder()
            contentBuilder.setContent(thread.body["content"] as String?)
            for (img in (thread.body["images"] as List<String>?)!!) {
                contentBuilder.addImages(CommonGrpcApi.Image.newBuilder().setUrl(img).build())
            }

            builder.setPlainContent(contentBuilder.build())
        } else if (bodyCase == ThreadGrpcApi.Thread.BodyCase.MARKDOWNCONTENT) {
            builder.setMarkdownContent(
                CommonGrpcApi.MarkdownContent.newBuilder()
                    .setContent(thread.body["content"] as String?).build()
            )
        }

        return builder.build()
    }

    override fun createThread(request: CreateThreadReq, responseObserver: StreamObserver<CreateThreadResp>) {
        val uid = AuthInterceptor.UID.get()
        val nowTime = Date()
        val thread = Thread()
            .withTitle(request.title)
            .withForumId(request.forumId)
            .withUid(uid)
            .withCreateAt(nowTime)
            .withLastUpdate(nowTime)
            .withUpdateAt(nowTime)
            .withLikes(0)
            .withPosts(0)
            .withDigest(false)
            .withPinned(false)

        thread.bodyType = request.bodyCase.number
        // Store content according to type
        val content = HashMap<String?, Any?>()
        if (request.bodyCase == CreateThreadReq.BodyCase.PLAINCONTENT) {
            content["content"] = request.plainContent.content
            // Store image URLs
            val imgList = ArrayList<String>()
            for (image in request.plainContent.imagesList) {
                imgList.add(image.url)
            }
            content["images"] = imgList
        } else if (request.bodyCase == CreateThreadReq.BodyCase.MARKDOWNCONTENT) {
            content["content"] = request.markdownContent.content
        }
        thread.body = content

        if (!threadService.create(thread)) throw RuntimeException("threadService.create returned false")

        val resp = CreateThreadResp
            .newBuilder().setThreadId(thread.id).build()
        responseObserver.onNext(resp)
        responseObserver.onCompleted()
    }

    override fun removeThread(request: RemoveThreadReq, responseObserver: StreamObserver<Empty>) {
        val uid = AuthInterceptor.UID.get()
        val thread = threadService.getById(request.threadId)
        // Check if the user has privilege to remove the thread
        if (thread == null) {
            responseObserver.onError(
                Status.INVALID_ARGUMENT
                    .withDescription("Thread does not exist").asException()
            )
            return
        }
        val threadUid = thread.uid
        if (threadUid == null || threadUid != uid) {
            responseObserver.onError(
                Status.PERMISSION_DENIED
                    .withDescription("Not the owner of the thread").asException()
            )
            return
        }

        // Check passed, remove thread and all its posts
        if (!threadService.hardRemove(request.threadId)) throw RuntimeException("threadService.hardRemove returned false")

        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    override fun updateThread(request: UpdateThreadReq, responseObserver: StreamObserver<Empty>) {
        if (!threadService.update(
                request.id,
                request.title,
                request.body
            )
        ) throw RuntimeException("threadService.update returned false")
        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    override fun getThreads(request: GetThreadsReq, responseObserver: StreamObserver<GetThreadsResp>) {
        val uid = AuthInterceptor.UID.get()
        val forumId = request.forumId
        val respBuilder = GetThreadsResp.newBuilder()
        val threads = threadService[request.offset, request.count, forumId, uid, request.orderingValue]
        threads.forEach(
            Consumer { thread: ThreadWithInfo -> respBuilder.addThreads(buildThread(thread)) }
        )

        responseObserver.onNext(respBuilder.build())
        responseObserver.onCompleted()
    }

    override fun likeThread(request: LikeThreadReq, responseObserver: StreamObserver<Empty>) {
        val uid = AuthInterceptor.UID.get()
        if (request.like > 0) {
            if (!threadService.upvote(
                    request.threadId,
                    uid
                )
            ) throw RuntimeException("threadService.upvote returned false")
        } else if (request.like == 0) {
            if (!threadService.cancelVote(
                    request.threadId,
                    uid
                )
            ) throw RuntimeException("threadService.cancleVote returned false")
        } else {
            if (!threadService.downvote(
                    request.threadId,
                    uid
                )
            ) throw RuntimeException("threadService.downvote returned false")
        }
        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    override fun pinThread(request: PinThreadReq, responseObserver: StreamObserver<Empty>) {
        val uid = AuthInterceptor.UID.get()
        val thread = threadService.getById(request.threadId)
        val forum = forumService.getById(thread.forumId)
        if (forum.creatorUid != uid) {
            responseObserver.onError(Status.PERMISSION_DENIED.asException())
            return
        }

        if (!threadService.togglePinned(request.threadId)) throw RuntimeException("threadService.togglePinned returned false")
        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    override fun digestThread(request: DigestThreadReq, responseObserver: StreamObserver<Empty>) {
        val uid = AuthInterceptor.UID.get()
        val thread = threadService.getById(request.threadId)
        val forum = forumService.getById(thread.forumId)
        if (forum.creatorUid != uid) {
            responseObserver.onError(Status.PERMISSION_DENIED.asException())
            return
        }

        if (!threadService.toggleDigest(request.threadId)) throw RuntimeException("threadService.toggleDigest returned false")
        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    override fun getSavedThreads(request: GetSavedThreadsReq, responseObserver: StreamObserver<GetThreadsResp>) {
        val uid = AuthInterceptor.UID.get()
        val saved = threadService.getSaved(request.offset, request.count, uid)
        val builder = GetThreadsResp.newBuilder()
        saved.forEach(Consumer { thread: ThreadWithInfo ->
            builder.addThreads(buildThread(thread))
        })
        responseObserver.onNext(builder.build())
        responseObserver.onCompleted()
    }
}
