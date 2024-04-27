package io.xdea.xmux.forum.service

import io.xdea.xmux.forum.mapper.SavedPostMapper
import io.xdea.xmux.forum.mapper.SavedThreadMapper
import io.xdea.xmux.forum.model.SavedPost
import io.xdea.xmux.forum.model.SavedPostExample
import io.xdea.xmux.forum.model.SavedThread
import io.xdea.xmux.forum.model.SavedThreadExample
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SavedService @Autowired constructor(
    private val savedPostMapper: SavedPostMapper,
    private val savedThreadMapper: SavedThreadMapper
) {
    fun checkThreadSaved(uid: String?, threadId: Int): Boolean {
        val savedThreadExample = SavedThreadExample()
        savedThreadExample.createCriteria().andUidEqualTo(uid).andThreadIdEqualTo(threadId)
        return savedThreadMapper.countByExample(savedThreadExample) >= 1
    }

    fun checkPostSaved(uid: String?, postId: Int): Boolean {
        val savedPostExample = SavedPostExample()
        savedPostExample.createCriteria().andUidEqualTo(uid).andPostIdEqualTo(postId)
        return savedPostMapper.countByExample(savedPostExample) >= 1
    }

    fun saveThread(uid: String?, threadId: Int): Boolean {
        return savedThreadMapper.insertSelective(
            SavedThread()
                .withCreateAt(Date())
                .withThreadId(threadId)
                .withUid(uid)
        ) == 1
    }

    fun savePost(uid: String?, postId: Int): Boolean {
        return savedPostMapper.insertSelective(
            SavedPost()
                .withCreateAt(Date())
                .withPostId(postId)
                .withUid(uid)
        ) == 1
    }

    fun removeSavedThread(uid: String?, threadId: Int?): Boolean {
        val savedThreadExample = SavedThreadExample()
        savedThreadExample.createCriteria().andUidEqualTo(uid).andThreadIdEqualTo(threadId)
        return savedThreadMapper.deleteByExample(savedThreadExample) == 1
    }

    fun removeSavedPost(uid: String?, postId: Int?): Boolean {
        val savedPostExample = SavedPostExample()
        savedPostExample.createCriteria().andUidEqualTo(uid).andPostIdEqualTo(postId)
        return savedPostMapper.deleteByExample(savedPostExample) == 1
    }
}
