package io.xdea.xmux.forum.mapper;

import io.xdea.xmux.forum.model.Thread;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ThreadExtMapper {
    List<Thread> selectSaved(@Param("offset") int offset,
                             @Param("count") int count,
                             @Param("uid") String uid);

    int setUpdateTimeToNow(@Param("id") int id);

    int changeVote(@Param("id") int id, @Param("amount") int amount);

    int toggleDigest(@Param("id") int id);

    int togglePinned(@Param("id") int id);
}
