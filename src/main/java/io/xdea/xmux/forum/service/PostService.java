package io.xdea.xmux.forum.service;

import io.xdea.xmux.forum.mapper.PostMapper;
import io.xdea.xmux.forum.mapper.PostExtMapper;
import io.xdea.xmux.forum.model.Post;
import io.xdea.xmux.forum.model.PostExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostMapper postMapper;
    private final PostExtMapper postExtMapper;
    static private final String[] orderStr = {"create_at desc", "create_at asc", "like desc"};

    @Autowired
    public PostService(PostMapper postMapper, PostExtMapper postExtMapper) {
        this.postMapper = postMapper;
        this.postExtMapper = postExtMapper;
    }

    public boolean create(Post post) {
        return postMapper.insert(post) == 1;
    }

    public Post getById(int postId) {
        return postMapper.selectByPrimaryKey(postId);
    }

    public boolean hardRemove(int postId) {
        return postMapper.deleteByPrimaryKey(postId) == 1;
    }

    public List<Post> get(int pageNo, int pageSize, Integer threadId, int orderMethod) {
        var offset = pageNo * pageSize;
        final PostExample postExample = new PostExample();
        postExample.setLimit(pageSize);
        postExample.setOffset(offset);
        postExample.createCriteria().andThreadIdEqualTo(threadId);
        postExample.setOrderByClause("pinned desc" + orderStr[orderMethod]);
        return postMapper.selectByExample(postExample);
    }

    public List<Post> getUserPost(int pageNo, int pageSize, String uid) {
        var offset = pageNo * pageSize;
        final PostExample postExample = new PostExample();
        postExample.setLimit(pageSize);
        postExample.setOffset(offset);
        postExample.createCriteria().andUidEqualTo(uid);
        postExample.setOrderByClause("create_at desc");
        return postMapper.selectByExample(postExample);
    }

    public List<Post> getSaved(int pageNo, int pageSize, String uid) {
        var offset = pageNo * pageSize;
        return postExtMapper.selectSaved(pageSize, offset, uid);
    }
}
