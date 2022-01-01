package com.flyingblu.community.mapper;

import com.flyingblu.community.model.Community;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityExtMapper {
    List<Community> selectWithLimitOffset(@Param("limit") int limit,
                                          @Param("offset") int offset);

    int setDeleteTimeToNow(@Param("id") int id);
}
