package com.flyingblu.community.controller;

import com.flyingblu.community.model.Community;
import com.flyingblu.community.service.CommunityService;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

public class CommunityController {
    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    public String insert() {
        Community community = new Community();
        community.setTitle("New Com");
        community.setDescription("The first community!");
        communityService.insert(community);
        return community.getId().toString();
    }

}
