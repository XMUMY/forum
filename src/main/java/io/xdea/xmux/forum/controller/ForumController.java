package io.xdea.xmux.forum.controller;

import io.xdea.xmux.forum.service.GroupService;
import io.xdea.xmux.forum.service.PostService;
import io.xdea.xmux.forum.service.ReplyService;
import io.xdea.xmux.forum.service.SavedService;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class ForumController extends SavedController {
    @Autowired
    public ForumController(GroupService groupService, PostService postService,
                           ReplyService replyService, SavedService savedService) {
        super(groupService, postService, replyService, savedService);
    }
}
