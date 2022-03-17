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
     * Hard remove thread and all its posts
     *
     * @param id thread id
     * @return thread removed or not
     */
    @Transactional
    public boolean hardRemove(int id) {
        final PostExample postExample = new PostExample();
        postExample.createCriteria().andThreadIdEqualTo(id);
        postMapper.deleteByExample(postExample);
        return threadMapper.deleteByPrimaryKey(id) == 1;
    }

    public boolean renewUpdateTime(int id) {
        return threadExtMapper.setUpdateTimeToNow(id) == 1;
    }

    public boolean changePostsNo(int id, int amount) {
        return threadExtMapper.changePostsNo(id, amount) == 1;
    }

    @Transactional
    public boolean upvote(int threadId, String uid) {
        int amount = 1;
        final LikedThreadExample likedThreadExample = new LikedThreadExample();
        likedThreadExample.createCriteria().andThreadIdEqualTo(threadId).andUidEqualTo(uid);
        var oldLike = likedThreadMapper.selectByExample(likedThreadExample);
        if (oldLike.size() >= 1) {
            var likedThread = oldLike.get(0);
            if (!likedThread.getLiked()) {
                amount = 2;
                likedThread.setLiked(true);
                likedThread.setCreateAt(new Date());
                likedThreadMapper.updateByPrimaryKeySelective(likedThread);
            } else {
                amount = 0;
            }
        } else {
            likedThreadMapper.insertSelective(new LikedThread().withThreadId(threadId)
                    .withUid(uid).withLiked(true).withCreateAt(new Date()));
        }
        return threadExtMapper.changeVote(threadId, amount) == 1;
    }

    @Transactional
    public boolean downvote(int threadId, String uid) {
        int amount = -1;
        final LikedThreadExample likedThreadExample = new LikedThreadExample();
        likedThreadExample.createCriteria().andThreadIdEqualTo(threadId).andUidEqualTo(uid);
        var oldLike = likedThreadMapper.selectByExample(likedThreadExample);
        if (oldLike.size() >= 1) {
            var likedThread = oldLike.get(0);
            if (likedThread.getLiked()) {
                amount = -2;
                likedThread.setLiked(false);
                likedThread.setCreateAt(new Date());
                likedThreadMapper.updateByPrimaryKeySelective(likedThread);
            } else {
                amount = 0;
            }
        } else {
            likedThreadMapper.insertSelective(new LikedThread().withThreadId(threadId)
                    .withUid(uid).withLiked(false).withCreateAt(new Date()));
        }
        return threadExtMapper.changeVote(threadId, amount) == 1;
    }

    @Transactional
    public boolean cancelVote(int threadId, String uid) {
        final LikedThreadExample likedThreadExample = new LikedThreadExample();
        likedThreadExample.createCriteria().andThreadIdEqualTo(threadId).andUidEqualTo(uid);
        var oldLike = likedThreadMapper.selectByExample(likedThreadExample);
        if (oldLike.size() >= 1) {
            var likedThread = oldLike.get(0);
            likedThreadMapper.deleteByPrimaryKey(likedThread.getId());
            if (likedThread.getLiked()) {
                return threadExtMapper.changeVote(threadId, -1) == 1;
            } else {
                return threadExtMapper.changeVote(threadId, 1) == 1;
            }
        } else {
            return true;
        }
    }

    public boolean toggleDigest(int id) {
        return threadExtMapper.toggleDigest(id) == 1;
    }

    public boolean togglePinned(int id) {
        return threadExtMapper.togglePinned(id) == 1;
    }

    public boolean update(int id, String title, String body) {
        final Date nowTime = new Date();
        return threadMapper.updateByPrimaryKeySelective(new Thread()
                .withId(id).withTitle(title).withBody(body)
                .withUpdateAt(nowTime).withLastUpdate(nowTime)) == 1;
    }
}
