package io.xdea.xmux.forum.mapper;

import io.xdea.xmux.forum.model.Thread;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ThreadExtMapper {
    List<Thread> selectSaved(@Param("limit") int limit,
                             @Param("offset") int offset,
                             @Param("uid") String uid);

    int setUpdateTimeToNow(@Param("id") int id);

    int incVote(@Param("id") int id);

    int decVote(@Param("id") int id);

    int toggleBest(@Param("id") int id);

    int toggleTop(@Param("id") int id);
}
