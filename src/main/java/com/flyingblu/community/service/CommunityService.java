package com.flyingblu.community.service;

import com.flyingblu.community.mapper.CommunityMapper;
import com.flyingblu.community.model.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {
    private final CommunityMapper communityMapper;

    @Autowired
    public CommunityService(CommunityMapper communityMapper) {
        this.communityMapper = communityMapper;
    }

    public boolean insert(Community community) {
        return communityMapper.insert(community) == 1;
    }
}
