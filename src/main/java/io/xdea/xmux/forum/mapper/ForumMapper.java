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
     * @mbg.generated Mon Mar 14 12:22:54 CST 2022
     */
    long countByExample(ForumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Mon Mar 14 12:22:54 CST 2022
     */
    int deleteByExample(ForumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Mon Mar 14 12:22:54 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Mon Mar 14 12:22:54 CST 2022
     */
    int insert(Forum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Mon Mar 14 12:22:54 CST 2022
     */
    int insertSelective(Forum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Mon Mar 14 12:22:54 CST 2022
     */
    List<Forum> selectByExampleWithRowbounds(ForumExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Mon Mar 14 12:22:54 CST 2022
     */
    List<Forum> selectByExample(ForumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Mon Mar 14 12:22:54 CST 2022
     */
    Forum selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Mon Mar 14 12:22:54 CST 2022
     */
    int updateByExampleSelective(@Param("record") Forum record, @Param("example") ForumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Mon Mar 14 12:22:54 CST 2022
     */
    int updateByExample(@Param("record") Forum record, @Param("example") ForumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Mon Mar 14 12:22:54 CST 2022
     */
    int updateByPrimaryKeySelective(Forum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.forum
     *
     * @mbg.generated Mon Mar 14 12:22:54 CST 2022
     */
    int updateByPrimaryKey(Forum record);
}