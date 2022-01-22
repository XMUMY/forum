package io.xdea.xmux.forum.service;

import io.xdea.xmux.forum.mapper.NotifMapper;
import io.xdea.xmux.forum.model.Notif;
import io.xdea.xmux.forum.model.NotifExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifService {
    final private NotifMapper notifMapper;

    @Autowired
    public NotifService(NotifMapper notifMapper) {
        this.notifMapper = notifMapper;
    }

    public Long getUserNotifNum(String uid) {
        NotifExample notifExample = new NotifExample();
        notifExample.createCriteria().andUidEqualTo(uid).andHasReadEqualTo(false);
        return notifMapper.countByExample(notifExample);
    }

    public List<Notif> get(String uid, int pageNo, int pageSize) {
        int offset = pageNo * pageSize;
        NotifExample notifExample = new NotifExample();
        notifExample.setLimit(pageSize);
        notifExample.setOffset(offset);
        notifExample.createCriteria().andUidEqualTo(uid);
        notifExample.setOrderByClause("create_time DESC");
        return notifMapper.selectByExample(notifExample);
    }

    public int setAsRead(String uid, List<Integer> ids) {
        NotifExample notifExample = new NotifExample();
        notifExample.createCriteria().andUidEqualTo(uid).andIdIn(ids);
        Notif notif = new Notif().withHasRead(true);
        return notifMapper.updateByExampleSelective(notif, notifExample);
    }
}
