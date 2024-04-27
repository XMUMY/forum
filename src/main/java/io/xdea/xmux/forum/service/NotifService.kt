package io.xdea.xmux.forum.service

import io.xdea.xmux.forum.mapper.NotifExtMapper
import io.xdea.xmux.forum.mapper.NotifMapper
import io.xdea.xmux.forum.model.Notif
import io.xdea.xmux.forum.model.NotifExample
import io.xdea.xmux.forum.model.NotifWithContent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NotifService @Autowired constructor(
    private val notifMapper: NotifMapper,
    private val notifExtMapper: NotifExtMapper
) {
    fun getUserNotifNum(uid: String?): Long {
        val notifExample = NotifExample()
        notifExample.createCriteria().andUidEqualTo(uid).andHasReadEqualTo(false)
        return notifMapper.countByExample(notifExample)
    }

    operator fun get(uid: String?, pageNo: Int, pageSize: Int): List<NotifWithContent> {
        val offset = pageNo * pageSize
        return notifExtMapper.selectWithLimitOffset(pageSize, offset, uid)
    }

    fun setAsRead(uid: String?, ids: List<Int?>?): Int {
        val notifExample = NotifExample()
        notifExample.createCriteria().andUidEqualTo(uid).andIdIn(ids)
        val notif = Notif().withHasRead(true)
        return notifMapper.updateByExampleSelective(notif, notifExample)
    }

    fun createNotif(notif: Notif?): Boolean {
        return notifMapper.insertSelective(notif) == 1
    }
}
