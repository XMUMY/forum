package io.xdea.xmux.forum.service

import io.xdea.xmux.forum.mapper.LikedPostMapper
import io.xdea.xmux.forum.mapper.PostExtMapper
import io.xdea.xmux.forum.mapper.PostMapper
import io.xdea.xmux.forum.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
open class PostService @Autowired constructor(
    private val postMapper: PostMapper,
    private val postExtMapper: PostExtMapper,
    private val likedPostMapper: LikedPostMapper,
    private val threadService: ThreadService,
    @param:Qualifier("orderingStrList") private val orderStr: Array<String>
) {
    @Transactional
    open fun create(post: Post): Boolean {
        val threadId = post.threadId
        val belongThread = threadService.getById(threadId)
        post.forumId = belongThread!!.forumId
        threadService.renewUpdateTime(threadId)
        threadService.changePostsNo(threadId, 1)
        return postMapper.insertSelective(post) == 1
    }

    fun getById(postId: Int): Post {
        return postMapper.selectByPrimaryKey(postId)
    }

    @Transactional
    open fun hardRemove(postId: Int, postThreadId: Int): Boolean {
        val postExample = PostExample()
        postExample.createCriteria().andParentIdEqualTo(postId)
        val deleteNo = postMapper.deleteByExample(postExample)
        val selfDelNo = postMapper.deleteByPrimaryKey(postId)
        threadService.changePostsNo(postThreadId, -(deleteNo + selfDelNo))
        return selfDelNo == 1
    }

    operator fun get(offset: Int, count: Int, threadId: Int?, uid: String?, orderMethod: Int): List<PostWithInfo> {
        return postExtMapper.select(offset, count, threadId!!, uid, orderStr[orderMethod])
    }

    fun getLvl2(offset: Int, count: Int, parentId: Int?, uid: String?, orderMethod: Int): List<PostWithInfo> {
        return postExtMapper.selectLvl2(offset, count, parentId!!, uid, orderStr[orderMethod])
    }

    fun getTree(limit: Int, startPostId: Int?, uid: String?, ordering: Int): List<PostWithInfo> {
        return postExtMapper.selectTree(limit, startPostId!!, uid, orderStr[ordering])
    }

    fun getUserPost(offset: Int, count: Int, uid: String?, ordering: Int): List<PostWithInfo> {
        return postExtMapper.selectByUser(offset, count, uid, orderStr[ordering])
    }

    fun getSaved(offset: Int, count: Int, uid: String?): List<PostWithInfo> {
        return postExtMapper.selectSaved(count, offset, uid)
    }

    @Transactional
    open fun upvote(postId: Int, uid: String?): Boolean {
        var amount = 1
        val likedPostExample = LikedPostExample()
        likedPostExample.createCriteria().andPostIdEqualTo(postId).andUidEqualTo(uid)
        val oldLike = likedPostMapper.selectByExample(likedPostExample)
        if (oldLike.size >= 1) {
            val likedPost = oldLike[0]
            if (!likedPost.liked) {
                amount = 2
                likedPost.liked = true
                likedPost.createAt = Date()
                likedPostMapper.updateByPrimaryKeySelective(likedPost)
            } else {
                amount = 0
            }
        } else {
            likedPostMapper.insertSelective(
                LikedPost().withPostId(postId)
                    .withUid(uid).withLiked(true).withCreateAt(Date())
            )
        }
        return postExtMapper.changeVote(postId, amount) == 1
    }

    @Transactional
    open fun downvote(postId: Int, uid: String?): Boolean {
        var amount = -1
        val likedPostExample = LikedPostExample()
        likedPostExample.createCriteria().andPostIdEqualTo(postId).andUidEqualTo(uid)
        val oldLike = likedPostMapper.selectByExample(likedPostExample)
        if (oldLike.size >= 1) {
            val likedPost = oldLike[0]
            if (likedPost.liked) {
                amount = -2
                likedPost.liked = true
                likedPost.createAt = Date()
                likedPostMapper.updateByPrimaryKeySelective(likedPost)
            } else {
                amount = 0
            }
        } else {
            likedPostMapper.insertSelective(
                LikedPost().withPostId(postId)
                    .withUid(uid).withLiked(true).withCreateAt(Date())
            )
        }
        return postExtMapper.changeVote(postId, amount) == 1
    }

    @Transactional
    open fun cancelVote(postId: Int, uid: String?): Boolean {
        val likedPostExample = LikedPostExample()
        likedPostExample.createCriteria().andPostIdEqualTo(postId).andUidEqualTo(uid)
        val oldLike = likedPostMapper.selectByExample(likedPostExample)
        if (oldLike.size >= 1) {
            val likedPost = oldLike[0]
            likedPostMapper.deleteByPrimaryKey(likedPost.id)
            return if (likedPost.liked) {
                postExtMapper.changeVote(postId, -1) == 1
            } else {
                postExtMapper.changeVote(postId, 1) == 1
            }
        } else {
            return true
        }
    }

    fun togglePinned(id: Int): Boolean {
        return postExtMapper.togglePinned(id) == 1
    }
}
