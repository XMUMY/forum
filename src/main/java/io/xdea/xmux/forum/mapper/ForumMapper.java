package io.xdea.xmux.forum.mapper;

import io.xdea.xmux.forum.model.Forum;
import io.xdea.xmux.forum.model.ForumExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface ForumMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    long countByExample(ForumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    int deleteByExample(ForumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    int insert(Forum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    int insertSelective(Forum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    List<Forum> selectByExampleWithRowbounds(ForumExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    List<Forum> selectByExample(ForumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    Forum selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    int updateByExampleSelective(@Param("record") Forum record, @Param("example") ForumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    int updateByExample(@Param("record") Forum record, @Param("example") ForumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    int updateByPrimaryKeySelective(Forum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    int updateByPrimaryKey(Forum record);
}