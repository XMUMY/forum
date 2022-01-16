package io.xdea.xmux.forum.mapper;

import io.xdea.xmux.forum.model.Post;
import io.xdea.xmux.forum.model.PostWithGroupName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostExtMapper {
    List<Post> selectWithLimitOffset(@Param("limit") int limit,
                                     @Param("offset") int offset,
                                     @Param("cId") List<Integer> groupIds);

    List<Post> selectWithUid(@Param("limit") int limit,
                             @Param("offset") int offset,
                             @Param("cId") List<Integer> groupIds,
                             @Param("uid") String uid);

    int setDeleteTimeToNow(@Param("id") int id);

    int setUpdateTimeToNow(@Param("id") int id);

    int incVote(@Param("id") int id);

    int decVote(@Param("id") int id);

    int toggleBest(@Param("id") int id);

    int toggleTop(@Param("id") int id);
}
