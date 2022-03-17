package io.xdea.xmux.forum.mapper;

import io.xdea.xmux.forum.model.PostWithInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostExtMapper {
    List<PostWithInfo> selectSaved(@Param("limit") int limit,
                                   @Param("offset") int offset,
                                   @Param("uid") String uid);

    List<PostWithInfo> selectTree(@Param("limit") int limit,
                                  @Param("startId") int startId,
                                  @Param("uid") String uid,
                                  @Param("orderStr") String orderStr);

    List<PostWithInfo> select(@Param("offset") int offset,
                              @Param("count") int count,
                              @Param("threadId") int threadId,
                              @Param("uid") String uid,
                              @Param("ordering") String ordering);

    List<PostWithInfo> selectByUser(@Param("offset") int offset,
                                    @Param("count") int count,
                                    @Param("uid") String uid,
                                    @Param("ordering") String ordering);

    int changeVote(@Param("id") int id, @Param("amount") int amount);

    int togglePinned(@Param("id") int id);

}
