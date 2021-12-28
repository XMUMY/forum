package com.flyingblu.community.mapper;

import static com.flyingblu.community.mapper.CommunityDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.flyingblu.community.model.Community;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface CommunityMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1967595+08:00", comments="Source Table: community.community")
    BasicColumn[] selectList = BasicColumn.columnList(id, title, description, createTime, deleteTime);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1861037+08:00", comments="Source Table: community.community")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1861037+08:00", comments="Source Table: community.community")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1861037+08:00", comments="Source Table: community.community")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT currval(pg_get_serial_sequence('community.community', 'id'));", keyProperty="record.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<Community> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1861037+08:00", comments="Source Table: community.community")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("CommunityResult")
    Optional<Community> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1861037+08:00", comments="Source Table: community.community")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="CommunityResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_time", property="deleteTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Community> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1861037+08:00", comments="Source Table: community.community")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1861037+08:00", comments="Source Table: community.community")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, community, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1861037+08:00", comments="Source Table: community.community")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, community, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1861037+08:00", comments="Source Table: community.community")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1861037+08:00", comments="Source Table: community.community")
    default int insert(Community record) {
        return MyBatis3Utils.insert(this::insert, record, community, c ->
            c.map(title).toProperty("title")
            .map(description).toProperty("description")
            .map(createTime).toProperty("createTime")
            .map(deleteTime).toProperty("deleteTime")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1861037+08:00", comments="Source Table: community.community")
    default int insertSelective(Community record) {
        return MyBatis3Utils.insert(this::insert, record, community, c ->
            c.map(title).toPropertyWhenPresent("title", record::getTitle)
            .map(description).toPropertyWhenPresent("description", record::getDescription)
            .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(deleteTime).toPropertyWhenPresent("deleteTime", record::getDeleteTime)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1967595+08:00", comments="Source Table: community.community")
    default Optional<Community> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, community, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1967595+08:00", comments="Source Table: community.community")
    default List<Community> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, community, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1967595+08:00", comments="Source Table: community.community")
    default List<Community> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, community, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1967595+08:00", comments="Source Table: community.community")
    default Optional<Community> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1967595+08:00", comments="Source Table: community.community")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, community, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1967595+08:00", comments="Source Table: community.community")
    static UpdateDSL<UpdateModel> updateAllColumns(Community record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(title).equalTo(record::getTitle)
                .set(description).equalTo(record::getDescription)
                .set(createTime).equalTo(record::getCreateTime)
                .set(deleteTime).equalTo(record::getDeleteTime);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1967595+08:00", comments="Source Table: community.community")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Community record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(title).equalToWhenPresent(record::getTitle)
                .set(description).equalToWhenPresent(record::getDescription)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(deleteTime).equalToWhenPresent(record::getDeleteTime);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1967595+08:00", comments="Source Table: community.community")
    default int updateByPrimaryKey(Community record) {
        return update(c ->
            c.set(title).equalTo(record::getTitle)
            .set(description).equalTo(record::getDescription)
            .set(createTime).equalTo(record::getCreateTime)
            .set(deleteTime).equalTo(record::getDeleteTime)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.1967595+08:00", comments="Source Table: community.community")
    default int updateByPrimaryKeySelective(Community record) {
        return update(c ->
            c.set(title).equalToWhenPresent(record::getTitle)
            .set(description).equalToWhenPresent(record::getDescription)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .set(deleteTime).equalToWhenPresent(record::getDeleteTime)
            .where(id, isEqualTo(record::getId))
        );
    }
}