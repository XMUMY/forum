package com.flyingblu.community.controller;

import com.flyingblu.community.model.Community;
import com.flyingblu.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("community")
public class CommunityController {
    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("insert")
    public String insert() {
        Community community = new Community();
        community.setTitle("New Com");
        community.setDescription("The first community!");
        communityService.insert(community);
        return community.getId().toString();
    }

}
