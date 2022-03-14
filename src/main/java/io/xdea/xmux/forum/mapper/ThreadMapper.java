package io.xdea.xmux.forum.mapper;

import io.xdea.xmux.forum.model.Thread;
import io.xdea.xmux.forum.model.ThreadExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface ThreadMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.thread
     *
     * @mbg.generated Mon Mar 14 20:41:03 CST 2022
     */
    long countByExample(ThreadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.thread
     *
     * @mbg.generated Mon Mar 14 20:41:03 CST 2022
     */
    int deleteByExample(ThreadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.thread
     *
     * @mbg.generated Mon Mar 14 20:41:03 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.thread
     *
     * @mbg.generated Mon Mar 14 20:41:03 CST 2022
     */
    int insert(Thread record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.thread
     *
     * @mbg.generated Mon Mar 14 20:41:03 CST 2022
     */
    int insertSelective(Thread record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.thread
     *
     * @mbg.generated Mon Mar 14 20:41:03 CST 2022
     */
    List<Thread> selectByExampleWithRowbounds(ThreadExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.thread
     *
     * @mbg.generated Mon Mar 14 20:41:03 CST 2022
     */
    List<Thread> selectByExample(ThreadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.thread
     *
     * @mbg.generated Mon Mar 14 20:41:03 CST 2022
     */
    Thread selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.thread
     *
     * @mbg.generated Mon Mar 14 20:41:03 CST 2022
     */
    int updateByExampleSelective(@Param("record") Thread record, @Param("example") ThreadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.thread
     *
     * @mbg.generated Mon Mar 14 20:41:03 CST 2022
     */
    int updateByExample(@Param("record") Thread record, @Param("example") ThreadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.thread
     *
     * @mbg.generated Mon Mar 14 20:41:03 CST 2022
     */
    int updateByPrimaryKeySelective(Thread record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.thread
     *
     * @mbg.generated Mon Mar 14 20:41:03 CST 2022
     */
    int updateByPrimaryKey(Thread record);
}