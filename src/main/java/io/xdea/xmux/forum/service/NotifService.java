package io.xdea.xmux.forum.service;

import io.xdea.xmux.forum.mapper.NotifExtMapper;
import io.xdea.xmux.forum.mapper.NotifMapper;
import io.xdea.xmux.forum.model.Notif;
import io.xdea.xmux.forum.model.NotifExample;
import io.xdea.xmux.forum.model.NotifWithContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Notif notif = new Notif().withHasRead(true);
        return notifMapper.updateByExampleSelective(notif, notifExample);
    }

    public boolean createNotif(Notif notif) {
        return notifMapper.insertSelective(notif) == 1;
    }
}
