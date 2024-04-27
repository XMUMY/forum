package io.xdea.xmux.forum.service

import io.xdea.xmux.forum.mapper.ForumMapper
import io.xdea.xmux.forum.mapper.MemberMapper
import io.xdea.xmux.forum.model.Forum
import io.xdea.xmux.forum.model.ForumExample
import io.xdea.xmux.forum.model.Member
import io.xdea.xmux.forum.model.MemberExample
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Consumer

@Service
class ForumService @Autowired constructor(
    private val forumMapper: ForumMapper,
    private val memberMapper: MemberMapper
) {
    fun create(forum: Forum?): Boolean {
        return forumMapper.insertSelective(forum) == 1
    }

    operator fun get(page: Int, numPerPage: Int): List<Forum> {
        val offset = page * numPerPage
        val forumExample = ForumExample()
        forumExample.limit = numPerPage
        forumExample.offset = offset
        return forumMapper.selectByExample(forumExample)
    }

    fun hardRemove(forumId: Int): Boolean {
        return forumMapper.deleteByPrimaryKey(forumId) == 1
    }

    /**
     * @param uid
     * @param forumId
     * @return false means the membership does not exist, vice versa.
     */
    fun checkMembershipExist(uid: String?, forumId: Int): Boolean {
        val memberExample = MemberExample()
        memberExample.createCriteria().andUidEqualTo(uid).andForumIdEqualTo(forumId)
        return memberMapper.countByExample(memberExample) > 0
    }

    fun createMembership(uid: String?, forumId: Int): Boolean {
        val member = Member()
            .withCreateAt(Date())
            .withUid(uid)
            .withForumId(forumId)
        return memberMapper.insertSelective(member) == 1
    }

    fun removeMembership(uid: String?, forumId: Int): Boolean {
        val memberExample = MemberExample()
        memberExample.createCriteria().andUidEqualTo(uid).andForumIdEqualTo(forumId)
        return memberMapper.deleteByExample(memberExample) == 1
    }

    fun getMemberForum(uid: String?): List<Forum> {
        val memberExample = MemberExample()
        memberExample.createCriteria().andUidEqualTo(uid)
        val forumIds: MutableList<Int> = ArrayList()
        memberMapper.selectByExample(memberExample).forEach(
            Consumer { member: Member -> forumIds.add(member.forumId) }
        )
        if (forumIds.size == 0) return ArrayList()

        val groupExample = ForumExample()
        groupExample.createCriteria().andIdIn(forumIds)
        return forumMapper.selectByExample(groupExample)
    }

    fun getById(forumId: Int): Forum {
        return forumMapper.selectByPrimaryKey(forumId)
    }
}
