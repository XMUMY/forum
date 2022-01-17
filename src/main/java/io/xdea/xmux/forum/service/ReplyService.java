package io.xdea.xmux.forum.service;

import io.xdea.xmux.forum.mapper.ReplyExtMapper;
import io.xdea.xmux.forum.mapper.ReplyMapper;
import io.xdea.xmux.forum.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {
    private final ReplyMapper replyMapper;
    private final ReplyExtMapper replyExtMapper;

    @Autowired
    public ReplyService(ReplyMapper replyMapper, ReplyExtMapper replyExtMapper) {
        this.replyMapper = replyMapper;
        this.replyExtMapper = replyExtMapper;
    }

    public boolean create(Reply reply) {
        return replyMapper.insert(reply) == 1;
    }

    public Reply getById(int replyId) {
        return replyMapper.selectByPrimaryKey(replyId);
    }

    public boolean softRemove(int replyId) {
        return replyExtMapper.setDeleteTimeToNow(replyId) == 1;
    }

    public List<Reply> get(int pageNo, int pageSize, Integer postId, Integer replyId, int orderMethod) {
        var offset = pageNo * pageSize;
        return replyExtMapper.selectWithLimitOffset(pageSize, offset, postId, replyId, orderMethod);
    }

    public List<Reply> getUserReply(int pageNo, int pageSize, String uid) {
        var offset = pageNo * pageSize;
        return replyExtMapper.selectWithUser(pageSize, offset, uid);
    }

    public List<Reply> getSaved(int pageNo, int pageSize, String uid) {
        var offset = pageNo * pageSize;
        return replyExtMapper.selectSaved(pageSize, offset, uid);
    }
}
