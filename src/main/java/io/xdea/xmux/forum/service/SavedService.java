package io.xdea.xmux.forum.service;

import io.xdea.xmux.forum.mapper.SavedPostMapper;
import io.xdea.xmux.forum.mapper.SavedReplyMapper;
import io.xdea.xmux.forum.model.SavedPost;
import io.xdea.xmux.forum.model.SavedPostExample;
import io.xdea.xmux.forum.model.SavedReply;
import io.xdea.xmux.forum.model.SavedReplyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SavedService {
    final private SavedPostMapper savedPostMapper;
    final private SavedReplyMapper savedReplyMapper;

    @Autowired
    public SavedService(SavedPostMapper savedPostMapper, SavedReplyMapper savedReplyMapper) {
        this.savedPostMapper = savedPostMapper;
        this.savedReplyMapper = savedReplyMapper;
    }

    public boolean checkPostSaved(String uid, int postId) {
        SavedPostExample savedPostExample = new SavedPostExample();
        savedPostExample.createCriteria().andUidEqualTo(uid).andPostIdEqualTo(postId);
        return savedPostMapper.countByExample(savedPostExample) >= 1;
    }

    public boolean checkReplySaved(String uid, int replyId) {
        SavedReplyExample savedReplyExample = new SavedReplyExample();
        savedReplyExample.createCriteria().andUidEqualTo(uid).andReplyIdEqualTo(replyId);
        return savedReplyMapper.countByExample(savedReplyExample) >= 1;
    }

    public boolean savePost(String uid, int postId) {
        return savedPostMapper.insert(new SavedPost()
                .withCreateTime(new Date())
                .withPostId(postId)
                .withUid(uid)) == 1;
    }

    public boolean saveReply(String uid, int replyId) {
        return savedReplyMapper.insert(new SavedReply()
                .withCreateTime(new Date())
                .withReplyId(replyId)
                .withUid(uid)) == 1;
    }

    public boolean removeSavedPost(String uid, Integer postId) {
        SavedPostExample savedPostExample = new SavedPostExample();
        savedPostExample.createCriteria().andUidEqualTo(uid).andPostIdEqualTo(postId);
        return savedPostMapper.deleteByExample(savedPostExample) == 1;
    }

    public boolean removeSavedReply(String uid, Integer replyId) {
        SavedReplyExample savedReplyExample = new SavedReplyExample();
        savedReplyExample.createCriteria().andUidEqualTo(uid).andReplyIdEqualTo(replyId);
        return savedReplyMapper.deleteByExample(savedReplyExample) == 1;
    }
}
