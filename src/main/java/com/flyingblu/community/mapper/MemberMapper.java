package com.flyingblu.community.mapper;

import static com.flyingblu.community.mapper.MemberDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.flyingblu.community.model.Member;
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
public interface MemberMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7023316+08:00", comments="Source Table: community.member")
    BasicColumn[] selectList = BasicColumn.columnList(id, uid, communityId, createTime);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7017779+08:00", comments="Source Table: community.member")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7017779+08:00", comments="Source Table: community.member")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7017779+08:00", comments="Source Table: community.member")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT currval(pg_get_serial_sequence('community.member', 'id'));", keyProperty="record.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<Member> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7017779+08:00", comments="Source Table: community.member")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("MemberResult")
    Optional<Member> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7017779+08:00", comments="Source Table: community.member")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="MemberResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="uid", property="uid", jdbcType=JdbcType.VARCHAR),
        @Result(column="community_id", property="communityId", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Member> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7017779+08:00", comments="Source Table: community.member")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7017779+08:00", comments="Source Table: community.member")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, member, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7017779+08:00", comments="Source Table: community.member")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, member, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7023316+08:00", comments="Source Table: community.member")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7023316+08:00", comments="Source Table: community.member")
    default int insert(Member record) {
        return MyBatis3Utils.insert(this::insert, record, member, c ->
            c.map(uid).toProperty("uid")
            .map(communityId).toProperty("communityId")
            .map(createTime).toProperty("createTime")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7023316+08:00", comments="Source Table: community.member")
    default int insertSelective(Member record) {
        return MyBatis3Utils.insert(this::insert, record, member, c ->
            c.map(uid).toPropertyWhenPresent("uid", record::getUid)
            .map(communityId).toPropertyWhenPresent("communityId", record::getCommunityId)
            .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7023316+08:00", comments="Source Table: community.member")
    default Optional<Member> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, member, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7023316+08:00", comments="Source Table: community.member")
    default List<Member> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, member, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7023316+08:00", comments="Source Table: community.member")
    default List<Member> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, member, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7028703+08:00", comments="Source Table: community.member")
    default Optional<Member> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7028703+08:00", comments="Source Table: community.member")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, member, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7028703+08:00", comments="Source Table: community.member")
    static UpdateDSL<UpdateModel> updateAllColumns(Member record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(uid).equalTo(record::getUid)
                .set(communityId).equalTo(record::getCommunityId)
                .set(createTime).equalTo(record::getCreateTime);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7028703+08:00", comments="Source Table: community.member")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Member record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(uid).equalToWhenPresent(record::getUid)
                .set(communityId).equalToWhenPresent(record::getCommunityId)
                .set(createTime).equalToWhenPresent(record::getCreateTime);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7028703+08:00", comments="Source Table: community.member")
    default int updateByPrimaryKey(Member record) {
        return update(c ->
            c.set(uid).equalTo(record::getUid)
            .set(communityId).equalTo(record::getCommunityId)
            .set(createTime).equalTo(record::getCreateTime)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7028703+08:00", comments="Source Table: community.member")
    default int updateByPrimaryKeySelective(Member record) {
        return update(c ->
            c.set(uid).equalToWhenPresent(record::getUid)
            .set(communityId).equalToWhenPresent(record::getCommunityId)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .where(id, isEqualTo(record::getId))
        );
    }
}