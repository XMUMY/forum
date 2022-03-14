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

    List<Post> selectTree(@Param("limit") int limit, @Param("startId") int startId,
                          @Param("orderStr") String orderStr);

    int changeVote(@Param("id") int id, @Param("amount") int amount);

    int togglePinned(@Param("id") int id);

}
