package com.flyingblu.community.service;

import com.flyingblu.community.mapper.PostMapper;
import com.flyingblu.community.model.Post;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.mybatis.dynamic.sql.SqlBuilder.select;

@Service
public class PostService {
    private final PostMapper postMapper;

    @Autowired
    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public boolean create(Post post) {
        int insert = postMapper.insert(post);
        return insert == 1;
    }

    public Post getById(int id) {
        Optional<Post> post = postMapper.selectByPrimaryKey(id);
        if (post.isEmpty())
            return null;
        return post.get();
    }

    public List<Post> getAll() {
        return postMapper.select(c -> c);
    }

    public boolean removeById(int id) {
        return postMapper.deleteByPrimaryKey(id) == 1;
    }
}
