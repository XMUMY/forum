package io.xdea.xmux.forum.service;

import io.xdea.xmux.forum.mapper.NotifExtMapper;
import io.xdea.xmux.forum.mapper.NotifMapper;
import io.xdea.xmux.forum.model.Notif;
import io.xdea.xmux.forum.model.NotifExample;
import io.xdea.xmux.forum.model.NotifWithContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class NotifService {
    final private NotifMapper notifMapper;
    final private NotifExtMapper notifExtMapper;

    @Autowired
    public NotifService(NotifMapper notifMapper, NotifExtMapper notifExtMapper) {
        this.notifMapper = notifMapper;
        this.notifExtMapper = notifExtMapper;
    }

    public Long getUserNotifNum(String uid) {
        NotifExample notifExample = new NotifExample();
        notifExample.createCriteria().andUidEqualTo(uid).andHasReadEqualTo(false);
        return notifMapper.countByExample(notifExample);
    }

    public List<NotifWithContent> get(String uid, int pageNo, int pageSize) {
        int offset = pageNo * pageSize;
        return notifExtMapper.selectWithLimitOffset(pageSize, offset, uid);
    }

    public int setAsRead(String uid, List<Integer> ids) {
        NotifExample notifExample = new NotifExample();
        notifExample.createCriteria().andUidEqualTo(uid).andIdIn(ids);
        Notif notif = new Notif().withHasRead(true).withReadAt(new Date());
        return notifMapper.updateByExampleSelective(notif, notifExample);
    }

    private boolean createNotif(Notif notif) {
        return notifMapper.insertSelective(notif) == 1;
    }

    public boolean createThreadLikeNotif(Integer threadId, String ownerUid, String senderUid) {
        var data = new HashMap<String, Object>();
        data.put("threadId", threadId);
        return createNotif(new Notif().withUid(ownerUid).withSenderUid(senderUid).withType(0).withData(data));
    }

    public boolean createPostLikeNotif(Integer postId, Integer threadId, String ownerUid, String senderUid) {
        var data = new HashMap<String, Object>();
        data.put("postId", postId);
        data.put("threadId", threadId);
        return createNotif(new Notif().withUid(ownerUid).withSenderUid(senderUid).withType(1).withData(data));
    }

    public boolean createNewPostOnThreadNotif(Integer threadId, Integer postId, String ownerUid, String senderUid) {
        var data = new HashMap<String, Object>();
        data.put("threadId", threadId);
        data.put("postId", postId);
        return createNotif(new Notif().withUid(ownerUid).withSenderUid(senderUid).withType(2).withData(data));
    }

    public boolean createNewReplyOnPostNotif(Integer threadId, Integer postId, Integer replyPostId, String ownerUid, String senderUid) {
        var data = new HashMap<String, Object>();
        data.put("threadId", threadId);
        data.put("postId", postId);
        data.put("replyPostId", replyPostId);
        return createNotif(new Notif().withUid(ownerUid).withSenderUid(senderUid).withType(3).withData(data));
    }
}
