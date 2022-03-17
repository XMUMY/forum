package io.xdea.xmux.forum.service;

import io.xdea.xmux.forum.mapper.SavedPostMapper;
import io.xdea.xmux.forum.mapper.SavedThreadMapper;
import io.xdea.xmux.forum.model.SavedPost;
import io.xdea.xmux.forum.model.SavedPostExample;
import io.xdea.xmux.forum.model.SavedThread;
import io.xdea.xmux.forum.model.SavedThreadExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SavedService {
    final private SavedPostMapper savedPostMapper;
    final private SavedThreadMapper savedThreadMapper;

    @Autowired
    public SavedService(SavedPostMapper savedPostMapper, SavedThreadMapper savedThreadMapper) {
        this.savedPostMapper = savedPostMapper;
        this.savedThreadMapper = savedThreadMapper;
    }

    public boolean checkThreadSaved(String uid, int threadId) {
        SavedThreadExample savedThreadExample = new SavedThreadExample();
        savedThreadExample.createCriteria().andUidEqualTo(uid).andThreadIdEqualTo(threadId);
        return savedThreadMapper.countByExample(savedThreadExample) >= 1;
    }

    public boolean checkPostSaved(String uid, int postId) {
        SavedPostExample savedPostExample = new SavedPostExample();
        savedPostExample.createCriteria().andUidEqualTo(uid).andPostIdEqualTo(postId);
        return savedPostMapper.countByExample(savedPostExample) >= 1;
    }

    public boolean saveThread(String uid, int threadId) {
        return savedThreadMapper.insertSelective(new SavedThread()
                .withCreateAt(new Date())
                .withThreadId(threadId)
                .withUid(uid)) == 1;
    }

    public boolean savePost(String uid, int postId) {
        return savedPostMapper.insertSelective(new SavedPost()
                .withCreateAt(new Date())
                .withPostId(postId)
                .withUid(uid)) == 1;
    }

    public boolean removeSavedThread(String uid, Integer threadId) {
        SavedThreadExample savedThreadExample = new SavedThreadExample();
        savedThreadExample.createCriteria().andUidEqualTo(uid).andThreadIdEqualTo(threadId);
        return savedThreadMapper.deleteByExample(savedThreadExample) == 1;
    }

    public boolean removeSavedPost(String uid, Integer postId) {
        SavedPostExample savedPostExample = new SavedPostExample();
        savedPostExample.createCriteria().andUidEqualTo(uid).andPostIdEqualTo(postId);
        return savedPostMapper.deleteByExample(savedPostExample) == 1;
    }
}
