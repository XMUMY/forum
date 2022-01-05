package io.xdea.xmux.forum.mapper;

import io.xdea.xmux.forum.model.Group;
import io.xdea.xmux.forum.model.GroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface GroupMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.group
     *
     * @mbg.generated Wed Jan 05 21:44:19 CST 2022
     */
    long countByExample(GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.group
     *
     * @mbg.generated Wed Jan 05 21:44:19 CST 2022
     */
    int deleteByExample(GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.group
     *
     * @mbg.generated Wed Jan 05 21:44:19 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.group
     *
     * @mbg.generated Wed Jan 05 21:44:19 CST 2022
     */
    int insert(Group record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.group
     *
     * @mbg.generated Wed Jan 05 21:44:19 CST 2022
     */
    int insertSelective(Group record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.group
     *
     * @mbg.generated Wed Jan 05 21:44:19 CST 2022
     */
    List<Group> selectByExampleWithRowbounds(GroupExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.group
     *
     * @mbg.generated Wed Jan 05 21:44:19 CST 2022
     */
    List<Group> selectByExample(GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.group
     *
     * @mbg.generated Wed Jan 05 21:44:19 CST 2022
     */
    Group selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.group
     *
     * @mbg.generated Wed Jan 05 21:44:19 CST 2022
     */
    int updateByExampleSelective(@Param("record") Group record, @Param("example") GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.group
     *
     * @mbg.generated Wed Jan 05 21:44:19 CST 2022
     */
    int updateByExample(@Param("record") Group record, @Param("example") GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.group
     *
     * @mbg.generated Wed Jan 05 21:44:19 CST 2022
     */
    int updateByPrimaryKeySelective(Group record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.group
     *
     * @mbg.generated Wed Jan 05 21:44:19 CST 2022
     */
    int updateByPrimaryKey(Group record);
}