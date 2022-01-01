package com.flyingblu.community.service;

import com.flyingblu.community.mapper.PostExtMapper;
import com.flyingblu.community.mapper.PostMapper;
import com.flyingblu.community.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostService {
    private final PostMapper postMapper;
    private final PostExtMapper postExtMapper;

    @Autowired
    public PostService(PostMapper postMapper, PostExtMapper postExtMapper) {
        this.postMapper = postMapper;
        this.postExtMapper = postExtMapper;
    }

    public boolean create(Post post) {
        int insert = postMapper.insert(post);
        return insert == 1;
    }

    public Post getById(int id) {
        return postMapper.selectByPrimaryKey(id);
    }

    public List<Post> get(int page, int numPerPage, List<Integer> communityIds) {
        int offset = page * numPerPage;
        return postExtMapper.selectWithLimitOffset(numPerPage, offset, communityIds);
    }

    public boolean softRemove(int id) {
        return postExtMapper.setDeleteTimeToNow(id) == 1;
    }

    public boolean renewUpdateTime(int id) {
        return postExtMapper.setUpdateTimeToNow(id) == 1;
    }

    public boolean upvote(int id) {
        return postExtMapper.incVote(id) == 1;
    }

    public boolean downvote(int id) {
        return postExtMapper.decVote(id) == 1;
    }

    public boolean toggleBest(int id) {
        return postExtMapper.toggleBest(id) == 1;
    }

    public boolean toggleTop(int id) {
        return postExtMapper.toggleTop(id) == 1;
    }
}
