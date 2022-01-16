package io.xdea.xmux.forum.mapper;

import io.xdea.xmux.forum.model.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyExtMapper {
    int setDeleteTimeToNow(@Param("id") int id);

    // Order method refers to the proto definition
    List<Reply> selectWithLimitOffset(@Param("limit") int limit,
                                      @Param("offset") int offset,
                                      @Param("postId") Integer refPostId,
                                      @Param("replyId") Integer refReplyId,
                                      @Param("order") int orderMethod);

    List<Reply> selectWithUser(@Param("limit") int limit,
                               @Param("offset") int offset,
                               @Param("uid") String uid);
}
