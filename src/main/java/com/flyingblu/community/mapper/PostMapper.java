package com.flyingblu.community.mapper;

import static com.flyingblu.community.mapper.PostDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.flyingblu.community.model.Post;
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
public interface PostMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    BasicColumn[] selectList = BasicColumn.columnList(id, uid, createTime, title, body, vote, communityId, topped, best, deleteTime);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT currval(pg_get_serial_sequence('community.post', 'id'));", keyProperty="record.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<Post> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("PostResult")
    Optional<Post> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="PostResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="uid", property="uid", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="body", property="body", jdbcType=JdbcType.VARCHAR),
        @Result(column="vote", property="vote", jdbcType=JdbcType.INTEGER),
        @Result(column="community_id", property="communityId", jdbcType=JdbcType.INTEGER),
        @Result(column="topped", property="topped", jdbcType=JdbcType.BIT),
        @Result(column="best", property="best", jdbcType=JdbcType.BIT),
        @Result(column="delete_time", property="deleteTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Post> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, post, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, post, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    default int insert(Post record) {
        return MyBatis3Utils.insert(this::insert, record, post, c ->
            c.map(uid).toProperty("uid")
            .map(createTime).toProperty("createTime")
            .map(title).toProperty("title")
            .map(body).toProperty("body")
            .map(vote).toProperty("vote")
            .map(communityId).toProperty("communityId")
            .map(topped).toProperty("topped")
            .map(best).toProperty("best")
            .map(deleteTime).toProperty("deleteTime")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    default int insertSelective(Post record) {
        return MyBatis3Utils.insert(this::insert, record, post, c ->
            c.map(uid).toPropertyWhenPresent("uid", record::getUid)
            .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(title).toPropertyWhenPresent("title", record::getTitle)
            .map(body).toPropertyWhenPresent("body", record::getBody)
            .map(vote).toPropertyWhenPresent("vote", record::getVote)
            .map(communityId).toPropertyWhenPresent("communityId", record::getCommunityId)
            .map(topped).toPropertyWhenPresent("topped", record::getTopped)
            .map(best).toPropertyWhenPresent("best", record::getBest)
            .map(deleteTime).toPropertyWhenPresent("deleteTime", record::getDeleteTime)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    default Optional<Post> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, post, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    default List<Post> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, post, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    default List<Post> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, post, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    default Optional<Post> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, post, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    static UpdateDSL<UpdateModel> updateAllColumns(Post record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(uid).equalTo(record::getUid)
                .set(createTime).equalTo(record::getCreateTime)
                .set(title).equalTo(record::getTitle)
                .set(body).equalTo(record::getBody)
                .set(vote).equalTo(record::getVote)
                .set(communityId).equalTo(record::getCommunityId)
                .set(topped).equalTo(record::getTopped)
                .set(best).equalTo(record::getBest)
                .set(deleteTime).equalTo(record::getDeleteTime);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Post record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(uid).equalToWhenPresent(record::getUid)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(title).equalToWhenPresent(record::getTitle)
                .set(body).equalToWhenPresent(record::getBody)
                .set(vote).equalToWhenPresent(record::getVote)
                .set(communityId).equalToWhenPresent(record::getCommunityId)
                .set(topped).equalToWhenPresent(record::getTopped)
                .set(best).equalToWhenPresent(record::getBest)
                .set(deleteTime).equalToWhenPresent(record::getDeleteTime);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    default int updateByPrimaryKey(Post record) {
        return update(c ->
            c.set(uid).equalTo(record::getUid)
            .set(createTime).equalTo(record::getCreateTime)
            .set(title).equalTo(record::getTitle)
            .set(body).equalTo(record::getBody)
            .set(vote).equalTo(record::getVote)
            .set(communityId).equalTo(record::getCommunityId)
            .set(topped).equalTo(record::getTopped)
            .set(best).equalTo(record::getBest)
            .set(deleteTime).equalTo(record::getDeleteTime)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    default int updateByPrimaryKeySelective(Post record) {
        return update(c ->
            c.set(uid).equalToWhenPresent(record::getUid)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .set(title).equalToWhenPresent(record::getTitle)
            .set(body).equalToWhenPresent(record::getBody)
            .set(vote).equalToWhenPresent(record::getVote)
            .set(communityId).equalToWhenPresent(record::getCommunityId)
            .set(topped).equalToWhenPresent(record::getTopped)
            .set(best).equalToWhenPresent(record::getBest)
            .set(deleteTime).equalToWhenPresent(record::getDeleteTime)
            .where(id, isEqualTo(record::getId))
        );
    }
}