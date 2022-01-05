package io.xdea.xmux.forum.controller;

import io.xdea.xmux.forum.service.GroupService;
import io.xdea.xmux.forum.service.PostService;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class ForumController extends PostController{
    @Autowired
    public ForumController(GroupService groupService, PostService postService) {
        super(groupService, postService);
    }
}
