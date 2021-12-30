package com.flyingblu.community.mapper;

import com.flyingblu.community.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostExtMapper {
    List<Post> selectWithLimitOffset(@Param("limit") int limit, @Param("offset") int offset);
}
