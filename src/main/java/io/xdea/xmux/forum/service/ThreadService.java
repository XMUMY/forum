package io.xdea.xmux.forum.service;

import io.xdea.xmux.forum.mapper.ThreadExtMapper;
import io.xdea.xmux.forum.mapper.ThreadMapper;
import io.xdea.xmux.forum.model.Thread;
import io.xdea.xmux.forum.model.ThreadExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ThreadService {
    private final ThreadMapper threadMapper;
    private final ThreadExtMapper threadExtMapper;

    @Autowired
    public ThreadService(ThreadMapper threadMapper, ThreadExtMapper threadExtMapper) {
        this.threadMapper = threadMapper;
        this.threadExtMapper = threadExtMapper;
    }

    public boolean create(Thread thread) {
        int insert = threadMapper.insert(thread);
        return insert == 1;
    }

    public Thread getById(int id) {
        return threadMapper.selectByPrimaryKey(id);
    }

    public List<Thread> get(int page, int numPerPage, List<Integer> forumIds, String uid) {
        int offset = page * numPerPage;
        final ThreadExample threadExample = new ThreadExample();
        threadExample.setLimit(numPerPage);
        threadExample.setOffset(offset);
        if (uid == null) {
            threadExample.createCriteria().andForumIdIn(forumIds);
        }
        else {
            threadExample.createCriteria().andForumIdIn(forumIds).andUidEqualTo(uid);
        }
        return threadMapper.selectByExample(threadExample);
    }

    public List<Thread> getSaved(int page, int numPerPage, String uid) {
        int offset = page * numPerPage;
        return threadExtMapper.selectSaved(numPerPage, offset, uid);
    }

    public boolean hardRemove(int id) {
        return threadMapper.deleteByPrimaryKey(id) == 1;
    }

    public boolean renewUpdateTime(int id) {
        return threadExtMapper.setUpdateTimeToNow(id) == 1;
    }

    public boolean upvote(int id) {
        return threadExtMapper.incVote(id) == 1;
    }

    public boolean downvote(int id) {
        return threadExtMapper.decVote(id) == 1;
    }

    public boolean toggleDigest(int id) {
        return threadExtMapper.toggleDigest(id) == 1;
    }

    public boolean togglePinned(int id) {
        return threadExtMapper.togglePinned(id) == 1;
    }
}
