package io.xdea.xmux.forum.controller

import com.google.protobuf.Empty
import com.google.protobuf.Timestamp
import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.xdea.xmux.forum.dto.CommonGrpcApi
import io.xdea.xmux.forum.dto.CommonGrpcApi.PlainContent
import io.xdea.xmux.forum.dto.PostGrpcApi
import io.xdea.xmux.forum.dto.PostGrpcApi.*
import io.xdea.xmux.forum.dto.SavedGrpcApi.GetSavedPostsReq
import io.xdea.xmux.forum.interceptor.AuthInterceptor
import io.xdea.xmux.forum.model.Post
import io.xdea.xmux.forum.model.PostWithInfo
import io.xdea.xmux.forum.service.ForumService
import io.xdea.xmux.forum.service.NotifService
import io.xdea.xmux.forum.service.PostService
import io.xdea.xmux.forum.service.ThreadService
import java.util.*
import java.util.function.Consumer

abstract class PostController protected constructor(
    forumService: ForumService,
    notifService: NotifService,
    threadService: ThreadService,
    protected val postService: PostService
) : ThreadController(forumService, notifService, threadService) {
    private fun buildPost(post: PostWithInfo): PostGrpcApi.Post {
        val builder = PostGrpcApi.Post.newBuilder()
            .setId(post.id)
            .setCreateAt(
                Timestamp.newBuilder()
                    .setSeconds(post.createAt.time / 1000)
            )
            .setLikes(post.likes)
            .setPinned(post.pinned)
            .setUid(post.uid)
            .setParentId(post.parentId)
            .setRefPostUid(if (post.refPostUid == null) "" else post.refPostUid)
            .setThreadId(post.threadId)
            .setRefPostId(post.refPostId)
            .setLiked(post.liked)
            .setSaved(post.saved)

        val contentCase = PostGrpcApi.Post.ContentCase
            .forNumber(post.contentType)
        if (contentCase == PostGrpcApi.Post.ContentCase.PLAINCONTENT) {
            val contentBuilder = PlainContent.newBuilder()
            contentBuilder.setContent(post.content["content"] as String?)
            for (img in (post.content["images"] as List<String>?)!!) {
                contentBuilder.addImages(CommonGrpcApi.Image.newBuilder().setUrl(img).build())
            }

            builder.setPlainContent(contentBuilder.build())
        } else if (contentCase == PostGrpcApi.Post.ContentCase.MARKDOWNCONTENT) {
            builder.setMarkdownContent(
                CommonGrpcApi.MarkdownContent.newBuilder()
                    .setContent(post.content["content"] as String?).build()
            )
        }
        return builder.build()
    }

    override fun getPosts(request: GetPostsReq, responseObserver: StreamObserver<GetPostsResp>) {
        val uid = AuthInterceptor.UID.get()
        val respBuilder = GetPostsResp.newBuilder()
        val posts = postService[request.offset, request.count, request.threadId, uid, request.orderingValue]
        posts.forEach(Consumer { post: PostWithInfo -> respBuilder.addPosts(buildPost(post)) })

        responseObserver.onNext(respBuilder.build())
        responseObserver.onCompleted()
    }

    override fun createPost(
        request: CreatePostReq,
        responseObserver: StreamObserver<CreatePostResp>
    ) {
        val uid = AuthInterceptor.UID.get()
        val nowTime = Date()
        val post = Post()
            .withCreateAt(nowTime)
            .withUpdateAt(nowTime)
            .withUid(uid)
            .withPinned(false)
            .withLikes(0)
            .withThreadId(request.threadId)
            .withRefPostId(request.refPostId)
            .withParentId(request.parentId)
            .withRefPostUid(request.refPostUid)

        post.contentType = request.contentCase.number
        // Store content according to type
        val content = HashMap<String?, Any?>()
        if (request.contentCase == CreatePostReq.ContentCase.PLAINCONTENT) {
            content["content"] = request.plainContent.content
            // Store image URLs
            val imgList = ArrayList<String>()
            for (image in request.plainContent.imagesList) {
                imgList.add(image.url)
            }
            content["images"] = imgList
        } else if (request.contentCase == CreatePostReq.ContentCase.MARKDOWNCONTENT) {
            content["content"] = request.markdownContent.content
        }
        post.content = content

        if (!postService.create(post)) throw RuntimeException("postService.create returned false")

        // TODO: Implement notif
        val resp = CreatePostResp
            .newBuilder().setPostId(post.id).build()
        responseObserver.onNext(resp)
        responseObserver.onCompleted()
    }

    override fun likePost(request: LikePostReq, responseObserver: StreamObserver<Empty>) {
        val uid = AuthInterceptor.UID.get()
        if (request.like > 0) {
            if (!postService.upvote(request.postId, uid)) throw RuntimeException("postService.upvote returned false")
        } else if (request.like == 0) {
            if (!postService.cancelVote(
                    request.postId,
                    uid
                )
            ) throw RuntimeException("postService.cancleVote returned false")
        } else {
            if (!postService.downvote(
                    request.postId,
                    uid
                )
            ) throw RuntimeException("postService.downvote returned false")
        }
        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    override fun pinPost(request: PinPostReq, responseObserver: StreamObserver<Empty>) {
        val uid = AuthInterceptor.UID.get()
        val post = postService.getById(request.postId)
        val thread = threadService.getById(post.threadId)
        if (thread.uid != uid) {
            responseObserver.onError(Status.PERMISSION_DENIED.asException())
            return
        }

        if (!postService.togglePinned(request.postId)) throw RuntimeException("postService.togglePinned returned false")
        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    override fun removePost(request: RemovePostReq, responseObserver: StreamObserver<Empty>) {
        val uid = AuthInterceptor.UID.get()
        val post = postService.getById(request.postId)
        // Check if the user has privilege to remove the post
        if (post == null) {
            responseObserver.onError(
                Status.INVALID_ARGUMENT
                    .withDescription("Resource does not exist").asException()
            )
            return
        }
        val postUid = post.uid
        if (postUid == null || postUid != uid) {
            responseObserver.onError(
                Status.PERMISSION_DENIED
                    .withDescription("Not the owner of the resource").asException()
            )
            return
        }

        // Remove post and also its children
        if (!postService.hardRemove(
                request.postId,
                post.threadId
            )
        ) throw RuntimeException("postService.hardRemove returned false")

        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    override fun getPostsByUid(request: GetPostsByUidReq, responseObserver: StreamObserver<GetPostsResp>) {
        val respBuilder = GetPostsResp.newBuilder()
        val posts = postService.getUserPost(request.offset, request.count, request.uid, request.orderingValue)
        posts.forEach(Consumer { post: PostWithInfo -> respBuilder.addPosts(buildPost(post)) })

        responseObserver.onNext(respBuilder.build())
        responseObserver.onCompleted()
    }

    override fun getSavedPosts(request: GetSavedPostsReq, responseObserver: StreamObserver<GetPostsResp>) {
        val uid = AuthInterceptor.UID.get()
        val respBuilder = GetPostsResp.newBuilder()
        val posts = postService.getSaved(request.offset, request.count, uid)
        posts.forEach(Consumer { post: PostWithInfo -> respBuilder.addPosts(buildPost(post)) })

        responseObserver.onNext(respBuilder.build())
        responseObserver.onCompleted()
    }

    override fun getPostsByParent(request: GetPostsByParentReq, responseObserver: StreamObserver<GetPostsResp>) {
        val uid = AuthInterceptor.UID.get()
        val respBuilder = GetPostsResp.newBuilder()
        val posts = postService.getLvl2(request.offset, request.count, request.parentId, uid, request.orderingValue)
        posts.forEach(Consumer { post: PostWithInfo -> respBuilder.addPosts(buildPost(post)) })

        responseObserver.onNext(respBuilder.build())
        responseObserver.onCompleted()
    }
}
