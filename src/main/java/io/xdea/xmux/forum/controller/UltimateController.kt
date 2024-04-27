package io.xdea.xmux.forum.controller

import io.sentry.spring.jakarta.tracing.SentryTransaction
import io.xdea.xmux.forum.service.*
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.beans.factory.annotation.Autowired

@GrpcService
@SentryTransaction(operation = "controller")
open class UltimateController @Autowired constructor(
    forumService: ForumService, notifService: NotifService, threadService: ThreadService,
    postService: PostService, savedService: SavedService
) : SavedController(forumService, notifService, threadService, postService, savedService)
