package io.xdea.xmux.forum.mapper;

import io.xdea.xmux.forum.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostExtMapper {
    List<Post> selectSaved(@Param("limit") int limit,
                           @Param("offset") int offset,
                           @Param("uid") String uid);
}
