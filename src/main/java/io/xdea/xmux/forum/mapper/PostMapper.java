package io.xdea.xmux.forum.mapper;

import io.xdea.xmux.forum.model.Post;
import io.xdea.xmux.forum.model.PostExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface PostMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Thu Jan 06 21:48:47 CST 2022
     */
    long countByExample(PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Thu Jan 06 21:48:47 CST 2022
     */
    int deleteByExample(PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Thu Jan 06 21:48:47 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Thu Jan 06 21:48:47 CST 2022
     */
    int insert(Post record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Thu Jan 06 21:48:47 CST 2022
     */
    int insertSelective(Post record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Thu Jan 06 21:48:47 CST 2022
     */
    List<Post> selectByExampleWithRowbounds(PostExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Thu Jan 06 21:48:47 CST 2022
     */
    List<Post> selectByExample(PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Thu Jan 06 21:48:47 CST 2022
     */
    Post selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Thu Jan 06 21:48:47 CST 2022
     */
    int updateByExampleSelective(@Param("record") Post record, @Param("example") PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Thu Jan 06 21:48:47 CST 2022
     */
    int updateByExample(@Param("record") Post record, @Param("example") PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Thu Jan 06 21:48:47 CST 2022
     */
    int updateByPrimaryKeySelective(Post record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Thu Jan 06 21:48:47 CST 2022
     */
    int updateByPrimaryKey(Post record);
}