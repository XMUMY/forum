package io.xdea.xmux.forum.service;

import io.xdea.xmux.forum.mapper.ForumMapper;
import io.xdea.xmux.forum.mapper.MemberMapper;
import io.xdea.xmux.forum.model.Forum;
import io.xdea.xmux.forum.model.ForumExample;
import io.xdea.xmux.forum.model.Member;
import io.xdea.xmux.forum.model.MemberExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ForumService {
    private final ForumMapper forumMapper;
    private final MemberMapper memberMapper;

    @Autowired
    public ForumService(ForumMapper forumMapper, MemberMapper memberMapper) {
        this.forumMapper = forumMapper;
        this.memberMapper = memberMapper;
    }

    public boolean create(Forum forum) {
        return forumMapper.insert(forum) == 1;
    }

    public List<Forum> get(int page, int numPerPage) {
        int offset = page * numPerPage;
        final ForumExample forumExample = new ForumExample();
        forumExample.setLimit(numPerPage);
        forumExample.setOffset(offset);
        return forumMapper.selectByExample(forumExample);
    }

    public boolean hardRemove(int forumId) {
        return forumMapper.deleteByPrimaryKey(forumId) == 1;
    }

    /**
     * @param uid
     * @param forumId
     * @return false means the membership does not exist, vice versa.
     */
    public boolean checkMembershipExist(String uid, int forumId) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andUidEqualTo(uid).andForumIdEqualTo(forumId);
        return memberMapper.countByExample(memberExample) > 0;
    }

    public boolean createMembership(String uid, int forumId) {
        Member member = new Member()
                .withCreateAt(new Date())
                .withUid(uid)
                .withForumId(forumId);
        return memberMapper.insert(member) == 1;
    }

    public boolean removeMembership(String uid, int forumId) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andUidEqualTo(uid).andForumIdEqualTo(forumId);
        return memberMapper.deleteByExample(memberExample) == 1;
    }

    public List<Forum> getMemberForum(String uid) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andUidEqualTo(uid);
        List<Integer> forumIds = new ArrayList<>();
        memberMapper.selectByExample(memberExample).forEach(
                member -> forumIds.add(member.getForumId())
        );
        if (forumIds.size() == 0)
            return new ArrayList<>();

        ForumExample groupExample = new ForumExample();
        groupExample.createCriteria().andIdIn(forumIds);
        return forumMapper.selectByExample(groupExample);
    }

    public Forum getById(int forumId) {
        return forumMapper.selectByPrimaryKey(forumId);
    }
}
