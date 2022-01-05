package io.xdea.xmux.forum.service;

import io.xdea.xmux.forum.mapper.GroupExtMapper;
import io.xdea.xmux.forum.mapper.GroupMapper;
import io.xdea.xmux.forum.mapper.MemberMapper;
import io.xdea.xmux.forum.model.Group;
import io.xdea.xmux.forum.model.Member;
import io.xdea.xmux.forum.model.MemberExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GroupService {
    private final GroupMapper groupMapper;
    private final GroupExtMapper groupExtMapper;
    private final MemberMapper memberMapper;

    @Autowired
    public GroupService(GroupMapper groupMapper, GroupExtMapper groupExtMapper, MemberMapper memberMapper) {
        this.groupMapper = groupMapper;
        this.groupExtMapper = groupExtMapper;
        this.memberMapper = memberMapper;
    }

    public boolean create(Group group) {
        return groupMapper.insert(group) == 1;
    }

    public List<Group> get(int page, int numPerPage) {
        int offset = page * numPerPage;
        return groupExtMapper.selectWithLimitOffset(numPerPage, offset);
    }

    public boolean softRemove(int groupId) {
        return groupExtMapper.setDeleteTimeToNow(groupId) == 1;
    }

    /**
     * @param uid
     * @param groupId
     * @return false means the membership does not exist, vice versa.
     */
    public boolean checkMembershipExist(String uid, int groupId) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andUidEqualTo(uid).andGroupIdEqualTo(groupId);
        return memberMapper.countByExample(memberExample) > 0;
    }

    public boolean createMembership(String uid, int groupId) {
        Member member = new Member()
                .withCreateTime(new Date())
                .withUid(uid)
                .withGroupId(groupId);
        return memberMapper.insert(member) == 1;
    }

    public boolean removeMembership(String uid, int groupId) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andUidEqualTo(uid).andGroupIdEqualTo(groupId);
        return memberMapper.deleteByExample(memberExample) == 1;
    }

    public Group getById(int groupId) {
        return groupMapper.selectByPrimaryKey(groupId);
    }
}
