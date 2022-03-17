package io.xdea.xmux.forum.mapper;

import io.xdea.xmux.forum.model.Thread;
import io.xdea.xmux.forum.model.ThreadWithInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ThreadExtMapper {
    List<ThreadWithInfo> selectSaved(@Param("offset") int offset,
                             @Param("count") int count,
                             @Param("uid") String uid);

    List<ThreadWithInfo> select(@Param("offset") int offset,
                                @Param("count") int count,
                                @Param("forumId") int forumId,
                                @Param("uid") String uid,
                                @Param("ordering") String ordering);

    int setUpdateTimeToNow(@Param("id") int id);

    int changePostsNo(@Param("id") int id, @Param("amount") int amount);

    int changeVote(@Param("id") int id, @Param("amount") int amount);

    int toggleDigest(@Param("id") int id);

    int togglePinned(@Param("id") int id);
}
