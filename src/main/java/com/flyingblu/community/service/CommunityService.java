package com.flyingblu.community.service;

import com.flyingblu.community.mapper.CommunityExtMapper;
import com.flyingblu.community.mapper.CommunityMapper;
import com.flyingblu.community.mapper.MemberMapper;
import com.flyingblu.community.model.Community;
import com.flyingblu.community.model.Member;
import com.flyingblu.community.model.MemberExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommunityService {
    private final CommunityMapper communityMapper;
    private final CommunityExtMapper communityExtMapper;
    private final MemberMapper memberMapper;

    @Autowired
    public CommunityService(CommunityMapper communityMapper, CommunityExtMapper communityExtMapper, MemberMapper memberMapper) {
        this.communityMapper = communityMapper;
        this.communityExtMapper = communityExtMapper;
        this.memberMapper = memberMapper;
    }

    public boolean create(Community community) {
        return communityMapper.insert(community) == 1;
    }

    public List<Community> get(int page, int numPerPage) {
        int offset = page * numPerPage;
        return communityExtMapper.selectWithLimitOffset(numPerPage, offset);
    }

    public boolean softRemove(int communityId) {
        return communityExtMapper.setDeleteTimeToNow(communityId) == 1;
    }

    /**
     * @param uid
     * @param communityId
     * @return false means the membership does not exist, vice versa.
     */
    public boolean checkMembershipExist(String uid, int communityId) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andUidEqualTo(uid).andCommunityIdEqualTo(communityId);
        return memberMapper.countByExample(memberExample) > 0;
    }

    public boolean createMembership(String uid, int communityId) {
        Member member = new Member()
                .withCreateTime(new Date())
                .withUid(uid)
                .withCommunityId(communityId);
        return memberMapper.insert(member) == 1;
    }

    public boolean removeMembership(String uid, int communityId) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andUidEqualTo(uid).andCommunityIdEqualTo(communityId);
        return memberMapper.deleteByExample(memberExample) == 1;
    }

    public Community getById(int communityId) {
        return communityMapper.selectByPrimaryKey(communityId);
    }
}
