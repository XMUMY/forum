package io.xdea.xmux.forum.service

import io.xdea.xmux.forum.mapper.LikedThreadMapper
import io.xdea.xmux.forum.mapper.PostMapper
import io.xdea.xmux.forum.mapper.ThreadExtMapper
import io.xdea.xmux.forum.mapper.ThreadMapper
import io.xdea.xmux.forum.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
open class ThreadService @Autowired constructor(
    private val threadMapper: ThreadMapper, private val threadExtMapper: ThreadExtMapper,
    private val postMapper: PostMapper, private val likedThreadMapper: LikedThreadMapper,
    @param:Qualifier("orderingStrList") private val orderStr: Array<String>
) {
    fun create(thread: Thread?): Boolean {
        val insert = threadMapper.insertSelective(thread)
        return insert == 1
    }

    fun getById(id: Int): Thread {
        return threadMapper.selectByPrimaryKey(id)
    }

    operator fun get(offset: Int, count: Int, forumId: Int, uid: String?, ordering: Int): List<ThreadWithInfo> {
        return threadExtMapper.select(offset, count, forumId, uid, orderStr[ordering])
    }

    fun getSaved(offset: Int, count: Int, uid: String?): List<ThreadWithInfo> {
        return threadExtMapper.selectSaved(offset, count, uid)
    }

    /**
     * Hard remove thread and all its posts
     *
     * @param id thread id
     * @return thread removed or not
     */
    @Transactional
    open fun hardRemove(id: Int): Boolean {
        val postExample = PostExample()
        postExample.createCriteria().andThreadIdEqualTo(id)
        postMapper.deleteByExample(postExample)
        return threadMapper.deleteByPrimaryKey(id) == 1
    }

    fun renewUpdateTime(id: Int): Boolean {
        return threadExtMapper.setUpdateTimeToNow(id) == 1
    }

    fun changePostsNo(id: Int, amount: Int): Boolean {
        return threadExtMapper.changePostsNo(id, amount) == 1
    }

    @Transactional
    open fun upvote(threadId: Int, uid: String?): Boolean {
        var amount = 1
        val likedThreadExample = LikedThreadExample()
        likedThreadExample.createCriteria().andThreadIdEqualTo(threadId).andUidEqualTo(uid)
        val oldLike = likedThreadMapper.selectByExample(likedThreadExample)
        if (oldLike.size >= 1) {
            val likedThread = oldLike[0]
            if (!likedThread.liked) {
                amount = 2
                likedThread.liked = true
                likedThread.createAt = Date()
                likedThreadMapper.updateByPrimaryKeySelective(likedThread)
            } else {
                amount = 0
            }
        } else {
            likedThreadMapper.insertSelective(
                LikedThread().withThreadId(threadId)
                    .withUid(uid).withLiked(true).withCreateAt(Date())
            )
        }
        return threadExtMapper.changeVote(threadId, amount) == 1
    }

    @Transactional
    open fun downvote(threadId: Int, uid: String?): Boolean {
        var amount = -1
        val likedThreadExample = LikedThreadExample()
        likedThreadExample.createCriteria().andThreadIdEqualTo(threadId).andUidEqualTo(uid)
        val oldLike = likedThreadMapper.selectByExample(likedThreadExample)
        if (oldLike.size >= 1) {
            val likedThread = oldLike[0]
            if (likedThread.liked) {
                amount = -2
                likedThread.liked = false
                likedThread.createAt = Date()
                likedThreadMapper.updateByPrimaryKeySelective(likedThread)
            } else {
                amount = 0
            }
        } else {
            likedThreadMapper.insertSelective(
                LikedThread().withThreadId(threadId)
                    .withUid(uid).withLiked(false).withCreateAt(Date())
            )
        }
        return threadExtMapper.changeVote(threadId, amount) == 1
    }

    @Transactional
    open fun cancelVote(threadId: Int, uid: String?): Boolean {
        val likedThreadExample = LikedThreadExample()
        likedThreadExample.createCriteria().andThreadIdEqualTo(threadId).andUidEqualTo(uid)
        val oldLike = likedThreadMapper.selectByExample(likedThreadExample)
        if (oldLike.size >= 1) {
            val likedThread = oldLike[0]
            likedThreadMapper.deleteByPrimaryKey(likedThread.id)
            return if (likedThread.liked) {
                threadExtMapper.changeVote(threadId, -1) == 1
            } else {
                threadExtMapper.changeVote(threadId, 1) == 1
            }
        } else {
            return true
        }
    }

    fun toggleDigest(id: Int): Boolean {
        return threadExtMapper.toggleDigest(id) == 1
    }

    fun togglePinned(id: Int): Boolean {
        return threadExtMapper.togglePinned(id) == 1
    }

    fun update(id: Int, title: String?, body: String?): Boolean {
        // TODO: fix update api
        val nowTime = Date()
        return threadMapper.updateByPrimaryKeySelective(
            Thread()
                .withId(id).withTitle(title)
                .withUpdateAt(nowTime).withLastUpdate(nowTime)
        ) == 1
    }
}
