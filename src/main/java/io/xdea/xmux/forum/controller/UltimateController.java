package io.xdea.xmux.forum.controller;

import io.sentry.spring.jakarta.tracing.SentryTransaction;
import io.xdea.xmux.forum.service.*;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
@SentryTransaction(operation = "controller")
public class UltimateController extends SavedController {
    @Autowired
    public UltimateController(ForumService forumService, NotifService notifService, ThreadService threadService,
                              PostService postService, SavedService savedService) {
        super(forumService, notifService, threadService, postService, savedService);
    }
}
