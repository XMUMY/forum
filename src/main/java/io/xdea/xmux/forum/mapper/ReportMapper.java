package io.xdea.xmux.forum.mapper;

import io.xdea.xmux.forum.model.Report;
import io.xdea.xmux.forum.model.ReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface ReportMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.report
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    long countByExample(ReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.report
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    int deleteByExample(ReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.report
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.report
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    int insert(Report record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.report
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    int insertSelective(Report record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.report
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    List<Report> selectByExampleWithRowbounds(ReportExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.report
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    List<Report> selectByExample(ReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.report
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    Report selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.report
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    int updateByExampleSelective(@Param("record") Report record, @Param("example") ReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.report
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    int updateByExample(@Param("record") Report record, @Param("example") ReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.report
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    int updateByPrimaryKeySelective(Report record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.report
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    int updateByPrimaryKey(Report record);
}