package io.xdea.xmux.forum.service;

import io.xdea.xmux.forum.mapper.LikedThreadMapper;
import io.xdea.xmux.forum.mapper.PostMapper;
import io.xdea.xmux.forum.mapper.ThreadExtMapper;
import io.xdea.xmux.forum.mapper.ThreadMapper;
import io.xdea.xmux.forum.model.*;
import io.xdea.xmux.forum.model.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ThreadService {
    private final ThreadMapper threadMapper;
    private final ThreadExtMapper threadExtMapper;
    private final PostMapper postMapper;
    private final LikedThreadMapper likedThreadMapper;
    private final String[] orderStr;

    @Autowired
    public ThreadService(ThreadMapper threadMapper, ThreadExtMapper threadExtMapper,
            PostMapper postMapper, LikedThreadMapper likedThreadMapper,
            @Qualifier("orderingStrList") String[] orderStr) {
        this.threadMapper = threadMapper;
        this.threadExtMapper = threadExtMapper;
        this.postMapper = postMapper;
        this.likedThreadMapper = likedThreadMapper;
        this.orderStr = orderStr;
    }

    public boolean create(Thread thread) {
        int insert = threadMapper.insertSelective(thread);
        return insert == 1;
    }

    public Thread getById(int id) {
        return threadMapper.selectByPrimaryKey(id);
    }

    public List<ThreadWithInfo> get(int offset, int count, int forumId, String uid, int ordering) {
        return threadExtMapper.select(offset, count, forumId, uid, orderStr[ordering]);
    }

    public List<ThreadWithInfo> getSaved(int offset, int count, String uid) {
        return threadExtMapper.selectSaved(offset, count, uid);
    }

    /**
     * Soft remove thread and all its posts by setting their status to deleted
     *
     * @param id thread id
     * @return true if thread and posts were successfully marked as deleted
     */
    @Transactional
    public boolean softRemove(int id) {
        // Set thread status to deleted
        Thread thread = new Thread();
        thread.setId(id);
        thread.setStatus(1); // 1 = deleted
        
        // Set all associated posts status to deleted
        Post post = new Post();
        post.setStatus(1); // 1 = deleted
        PostExample postExample = new PostExample();
        postExample.createCriteria().andThreadIdEqualTo(id);
        
        postMapper.updateByExampleSelective(post, postExample);
        return threadMapper.updateByPrimaryKeySelective(thread) == 1;
    }

    public boolean renewUpdateTime(int id) {
        return threadExtMapper.setUpdateTimeToNow(id) == 1;
    }

    public boolean changePostsNo(int id, int amount) {
        return threadExtMapper.changePostsNo(id, amount) == 1;
    }

    /**
     * @return true if the like is successful, false if the user has already liked
     *         the thread
     */
    @Transactional
    public boolean upvote(int threadId, String uid) {
        int amount = 1;
        final LikedThreadExample likedThreadExample = new LikedThreadExample();
        likedThreadExample.createCriteria().andThreadIdEqualTo(threadId).andUidEqualTo(uid);
        var oldLike = likedThreadMapper.selectByExample(likedThreadExample);

        // The user has already reacted to the thread
        if (oldLike.size() >= 1) {
            var likedThread = oldLike.get(0);
            // The user disliked the thread
            if (!likedThread.getLiked()) {
                amount = 2;
                likedThread.setLiked(true);
                likedThread.setCreateAt(new Date());
                int result = likedThreadMapper.updateByPrimaryKeySelective(likedThread);
                if (result != 1) {
                    throw new RuntimeException("likedThreadMapper.updateByPrimaryKeySelective returned " + result);
                }
            } else {
                // The user has already liked the thread
                amount = 0;
                return false;
            }

            // The user has not reacted to the thread
        } else {
            int result = likedThreadMapper.insertSelective(new LikedThread().withThreadId(threadId)
                    .withUid(uid).withLiked(true).withCreateAt(new Date()));
            if (result != 1) {
                throw new RuntimeException("likedThreadMapper.insertSelective returned " + result);
            }
        }
        int result = threadExtMapper.changeVote(threadId, amount);
        if (result != 1) {
            throw new RuntimeException("threadExtMapper.changeVote returned " + result);
        }
        return true;
    }

    /**
     * @return true if the downvote is successful, false if the user has already
     *         downvoted
     */
    @Transactional
    public boolean downvote(int threadId, String uid) {
        int amount = -1;
        final LikedThreadExample likedThreadExample = new LikedThreadExample();
        likedThreadExample.createCriteria().andThreadIdEqualTo(threadId).andUidEqualTo(uid);
        var oldLike = likedThreadMapper.selectByExample(likedThreadExample);

        // The user has already reacted to the thread
        if (oldLike.size() >= 1) {
            var likedThread = oldLike.get(0);
            // The user liked the thread
            if (likedThread.getLiked()) {
                amount = -2;
                likedThread.setLiked(false);
                likedThread.setCreateAt(new Date());
                int result = likedThreadMapper.updateByPrimaryKeySelective(likedThread);
                if (result != 1) {
                    throw new RuntimeException("likedThreadMapper.updateByPrimaryKeySelective returned " + result);
                }
            } else {
                // The user has already disliked the thread
                amount = 0;
                return false;
            }

            // The user has not reacted to the thread
        } else {
            int result = likedThreadMapper.insertSelective(new LikedThread().withThreadId(threadId)
                    .withUid(uid).withLiked(false).withCreateAt(new Date()));
            if (result != 1) {
                throw new RuntimeException("likedThreadMapper.insertSelective returned " + result);
            }
        }
        int result = threadExtMapper.changeVote(threadId, amount);
        if (result != 1) {
            throw new RuntimeException("threadExtMapper.changeVote returned " + result);
        }
        return true;
    }

    /**
     * @return true if the vote is cancelled, false if the user has not voted
     */
    @Transactional
    public boolean cancelVote(int threadId, String uid) {
        final LikedThreadExample likedThreadExample = new LikedThreadExample();
        likedThreadExample.createCriteria().andThreadIdEqualTo(threadId).andUidEqualTo(uid);
        var oldLike = likedThreadMapper.selectByExample(likedThreadExample);

        if (oldLike.size() >= 1) {
            var likedThread = oldLike.get(0);
            int result = likedThreadMapper.deleteByPrimaryKey(likedThread.getId());
            if (result != 1) {
                throw new RuntimeException("likedThreadMapper.deleteByPrimaryKey returned " + result);
            }

            int amount = likedThread.getLiked() ? -1 : 1;
            result = threadExtMapper.changeVote(threadId, amount);
            if (result != 1) {
                throw new RuntimeException("threadExtMapper.changeVote returned " + result);
            }
            return true;
        }
        return false;
    }

    public boolean toggleDigest(int id) {
        return threadExtMapper.toggleDigest(id) == 1;
    }

    public boolean togglePinned(int id) {
        return threadExtMapper.togglePinned(id) == 1;
    }

    public boolean update(int id, String title, String body) {
        // TODO: fix update api
        final Date nowTime = new Date();
        return threadMapper.updateByPrimaryKeySelective(new Thread()
                .withId(id).withTitle(title)
                .withUpdateAt(nowTime).withLastUpdate(nowTime)) == 1;
    }
}
