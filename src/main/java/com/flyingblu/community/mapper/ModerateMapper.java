package com.flyingblu.community.mapper;

import static com.flyingblu.community.mapper.ModerateDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.flyingblu.community.model.Moderate;
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
public interface ModerateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source Table: community.moderate")
    BasicColumn[] selectList = BasicColumn.columnList(id, uid, communityId, createTime, deleteTime);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source Table: community.moderate")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source Table: community.moderate")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source Table: community.moderate")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT currval(pg_get_serial_sequence('community.moderate', 'id'));", keyProperty="record.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<Moderate> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source Table: community.moderate")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("ModerateResult")
    Optional<Moderate> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source Table: community.moderate")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="ModerateResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="uid", property="uid", jdbcType=JdbcType.VARCHAR),
        @Result(column="community_id", property="communityId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_time", property="deleteTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Moderate> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source Table: community.moderate")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source Table: community.moderate")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, moderate, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source Table: community.moderate")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, moderate, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source Table: community.moderate")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source Table: community.moderate")
    default int insert(Moderate record) {
        return MyBatis3Utils.insert(this::insert, record, moderate, c ->
            c.map(uid).toProperty("uid")
            .map(communityId).toProperty("communityId")
            .map(createTime).toProperty("createTime")
            .map(deleteTime).toProperty("deleteTime")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source Table: community.moderate")
    default int insertSelective(Moderate record) {
        return MyBatis3Utils.insert(this::insert, record, moderate, c ->
            c.map(uid).toPropertyWhenPresent("uid", record::getUid)
            .map(communityId).toPropertyWhenPresent("communityId", record::getCommunityId)
            .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(deleteTime).toPropertyWhenPresent("deleteTime", record::getDeleteTime)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.moderate")
    default Optional<Moderate> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, moderate, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.moderate")
    default List<Moderate> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, moderate, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.moderate")
    default List<Moderate> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, moderate, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.moderate")
    default Optional<Moderate> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.moderate")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, moderate, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.moderate")
    static UpdateDSL<UpdateModel> updateAllColumns(Moderate record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(uid).equalTo(record::getUid)
                .set(communityId).equalTo(record::getCommunityId)
                .set(createTime).equalTo(record::getCreateTime)
                .set(deleteTime).equalTo(record::getDeleteTime);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.moderate")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Moderate record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(uid).equalToWhenPresent(record::getUid)
                .set(communityId).equalToWhenPresent(record::getCommunityId)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(deleteTime).equalToWhenPresent(record::getDeleteTime);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.moderate")
    default int updateByPrimaryKey(Moderate record) {
        return update(c ->
            c.set(uid).equalTo(record::getUid)
            .set(communityId).equalTo(record::getCommunityId)
            .set(createTime).equalTo(record::getCreateTime)
            .set(deleteTime).equalTo(record::getDeleteTime)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.moderate")
    default int updateByPrimaryKeySelective(Moderate record) {
        return update(c ->
            c.set(uid).equalToWhenPresent(record::getUid)
            .set(communityId).equalToWhenPresent(record::getCommunityId)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .set(deleteTime).equalToWhenPresent(record::getDeleteTime)
            .where(id, isEqualTo(record::getId))
        );
    }
}