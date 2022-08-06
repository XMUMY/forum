package io.xdea.xmux.forum.service;

import io.xdea.xmux.forum.mapper.SavedPostMapper;
import io.xdea.xmux.forum.mapper.SavedThreadMapper;
import io.xdea.xmux.forum.model.SavedPost;
import io.xdea.xmux.forum.model.SavedPostExample;
import io.xdea.xmux.forum.model.SavedThread;
import io.xdea.xmux.forum.model.SavedThreadExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SavedService {
    final private SavedPostMapper savedPostMapper;
    final private SavedThreadMapper savedThreadMapper;
    final private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public SavedService(SavedPostMapper savedPostMapper, SavedThreadMapper savedThreadMapper, RedisTemplate<String, String> redisTemplate) {
        this.savedPostMapper = savedPostMapper;
        this.savedThreadMapper = savedThreadMapper;
        this.redisTemplate = redisTemplate;
    }

    private String savedThreadKey(String uid) {
        return uid + ":strd";
    }

    private void checkAndLoadSavedThreadCache(String uid) {
        String key = savedThreadKey(uid);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key)))
            return;

        SavedThreadExample savedThreadExample = new SavedThreadExample();
        savedThreadExample.createCriteria().andUidEqualTo(uid);
        List<SavedThread> trds = savedThreadMapper.selectByExample(savedThreadExample);
        String[] trdIds = new String[trds.size()];
        for (int i = 0; i < trds.size(); i++) {
            trdIds[i] = trds.get(i).getThreadId().toString();
        }
        if (trdIds.length != 0) {
            redisTemplate.opsForSet().add(key, trdIds);
            redisTemplate.expire(key, 3650, TimeUnit.DAYS);
        }
    }

    public boolean checkThreadSaved(String uid, Integer threadId) {
        checkAndLoadSavedThreadCache(uid);
        return redisTemplate.opsForSet().isMember(savedThreadKey(uid), threadId.toString());
    }

    public boolean checkPostSaved(String uid, int postId) {
        SavedPostExample savedPostExample = new SavedPostExample();
        savedPostExample.createCriteria().andUidEqualTo(uid).andPostIdEqualTo(postId);
        return savedPostMapper.countByExample(savedPostExample) >= 1;
    }

    public boolean saveThread(String uid, Integer threadId) {
        String key = savedThreadKey(uid);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForSet().add(key, threadId.toString());
        }
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
        String key = savedThreadKey(uid);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForSet().remove(key, threadId.toString());
        }
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
