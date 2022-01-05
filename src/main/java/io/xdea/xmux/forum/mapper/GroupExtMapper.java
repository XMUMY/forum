package io.xdea.xmux.forum.mapper;

import io.xdea.xmux.forum.model.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupExtMapper {
    List<Group> selectWithLimitOffset(@Param("limit") int limit,
                                          @Param("offset") int offset);

    int setDeleteTimeToNow(@Param("id") int id);
}
