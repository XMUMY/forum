package io.xdea.xmux.forum.service;

import io.xdea.xmux.forum.mapper.LikedPostMapper;
import io.xdea.xmux.forum.mapper.PostMapper;
import io.xdea.xmux.forum.mapper.PostExtMapper;
import io.xdea.xmux.forum.model.*;
import io.xdea.xmux.forum.model.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PostService {
    private final PostMapper postMapper;
    private final PostExtMapper postExtMapper;
    private final LikedPostMapper likedPostMapper;
    private final ThreadService threadService;
    private final String[] orderStr;

    @Autowired
    public PostService(PostMapper postMapper, PostExtMapper postExtMapper,
                       LikedPostMapper likedPostMapper, ThreadService threadService, @Qualifier("orderingStrList") String[] orderStr) {
        this.postMapper = postMapper;
        this.postExtMapper = postExtMapper;
        this.likedPostMapper = likedPostMapper;
        this.threadService = threadService;
        this.orderStr = orderStr;
    }

    @Transactional
    public boolean create(Post post) {
        final Integer threadId = post.getThreadId();
        final Thread belongThread = threadService.getById(threadId);
        post.setForumId(belongThread.getForumId());
        threadService.renewUpdateTime(threadId);
        threadService.changePostsNo(threadId, 1);
        return postMapper.insertSelective(post) == 1;
    }

    public Post getById(int postId) {
        return postMapper.selectByPrimaryKey(postId);
    }

    @Transactional
    public boolean hardRemove(int postId, int postThreadId) {
        final PostExample postExample = new PostExample();
        postExample.createCriteria().andParentIdEqualTo(postId);
        final int deleteNo = postMapper.deleteByExample(postExample);
        final int selfDelNo = postMapper.deleteByPrimaryKey(postId);
        threadService.changePostsNo(postThreadId, -(deleteNo + selfDelNo));
        return selfDelNo == 1;
    }

    public List<PostWithInfo> get(int offset, int count, Integer threadId, String uid, int orderMethod) {
        return postExtMapper.select(offset, count, threadId, uid, orderStr[orderMethod]);
    }

    public List<PostWithInfo> getTree(int limit, Integer startPostId, String uid, int ordering) {
        return postExtMapper.selectTree(limit, startPostId, uid, orderStr[ordering]);
    }

    public List<PostWithInfo> getUserPost(int offset, int count, String uid, int ordering) {
        return postExtMapper.selectByUser(offset, count, uid, orderStr[ordering]);
    }

    public List<PostWithInfo> getSaved(int offset, int count, String uid) {
        return postExtMapper.selectSaved(count, offset, uid);
    }

    @Transactional
    public boolean upvote(int postId, String uid) {
        int amount = 1;
        final LikedPostExample likedPostExample = new LikedPostExample();
        likedPostExample.createCriteria().andPostIdEqualTo(postId).andUidEqualTo(uid);
        var oldLike = likedPostMapper.selectByExample(likedPostExample);
        if (oldLike.size() >= 1) {
            var likedPost = oldLike.get(0);
            if (!likedPost.getLiked()) {
                amount = 2;
                likedPost.setLiked(true);
                likedPost.setCreateAt(new Date());
                likedPostMapper.updateByPrimaryKeySelective(likedPost);
            } else {
                amount = 0;
            }
        } else {
            likedPostMapper.insertSelective(new LikedPost().withPostId(postId)
                    .withUid(uid).withLiked(true).withCreateAt(new Date()));
        }
        return postExtMapper.changeVote(postId, amount) == 1;
    }

    @Transactional
    public boolean downvote(int postId, String uid) {
        int amount = -1;
        final LikedPostExample likedPostExample = new LikedPostExample();
        likedPostExample.createCriteria().andPostIdEqualTo(postId).andUidEqualTo(uid);
        var oldLike = likedPostMapper.selectByExample(likedPostExample);
        if (oldLike.size() >= 1) {
            var likedPost = oldLike.get(0);
            if (likedPost.getLiked()) {
                amount = -2;
                likedPost.setLiked(true);
                likedPost.setCreateAt(new Date());
                likedPostMapper.updateByPrimaryKeySelective(likedPost);
            } else {
                amount = 0;
            }
        } else {
            likedPostMapper.insertSelective(new LikedPost().withPostId(postId)
                    .withUid(uid).withLiked(true).withCreateAt(new Date()));
        }
        return postExtMapper.changeVote(postId, amount) == 1;
    }

    @Transactional
    public boolean cancelVote(int postId, String uid) {
        final LikedPostExample likedPostExample = new LikedPostExample();
        likedPostExample.createCriteria().andPostIdEqualTo(postId).andUidEqualTo(uid);
        var oldLike = likedPostMapper.selectByExample(likedPostExample);
        if (oldLike.size() >= 1) {
            var likedPost = oldLike.get(0);
            likedPostMapper.deleteByPrimaryKey(likedPost.getId());
            if (likedPost.getLiked()) {
                return postExtMapper.changeVote(postId, -1) == 1;
            } else {
                return postExtMapper.changeVote(postId, 1) == 1;
            }
        } else {
            return true;
        }
    }

    public boolean togglePinned(int id) {
        return postExtMapper.togglePinned(id) == 1;
    }
}
