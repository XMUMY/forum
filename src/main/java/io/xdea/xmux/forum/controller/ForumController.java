package io.xdea.xmux.forum.controller;

import io.xdea.xmux.forum.service.*;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class ForumController extends SavedController {
    @Autowired
    public ForumController(GroupService groupService, NotifService notifService, PostService postService,
                           ReplyService replyService, SavedService savedService) {
        super(groupService, notifService, postService, replyService, savedService);
    }
}
