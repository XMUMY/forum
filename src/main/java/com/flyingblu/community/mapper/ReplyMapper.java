package com.flyingblu.community.mapper;

import static com.flyingblu.community.mapper.ReplyDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.flyingblu.community.model.Reply;
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
public interface ReplyMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    BasicColumn[] selectList = BasicColumn.columnList(id, content, createTime, uid, vote, refPostId, refReplyId, topped, deleteTime);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT currval(pg_get_serial_sequence('community.reply', 'id'));", keyProperty="record.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<Reply> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("ReplyResult")
    Optional<Reply> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="ReplyResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="uid", property="uid", jdbcType=JdbcType.VARCHAR),
        @Result(column="vote", property="vote", jdbcType=JdbcType.INTEGER),
        @Result(column="ref_post_id", property="refPostId", jdbcType=JdbcType.INTEGER),
        @Result(column="ref_reply_id", property="refReplyId", jdbcType=JdbcType.INTEGER),
        @Result(column="topped", property="topped", jdbcType=JdbcType.BIT),
        @Result(column="delete_time", property="deleteTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Reply> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, reply, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, reply, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    default int insert(Reply record) {
        return MyBatis3Utils.insert(this::insert, record, reply, c ->
            c.map(content).toProperty("content")
            .map(createTime).toProperty("createTime")
            .map(uid).toProperty("uid")
            .map(vote).toProperty("vote")
            .map(refPostId).toProperty("refPostId")
            .map(refReplyId).toProperty("refReplyId")
            .map(topped).toProperty("topped")
            .map(deleteTime).toProperty("deleteTime")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    default int insertSelective(Reply record) {
        return MyBatis3Utils.insert(this::insert, record, reply, c ->
            c.map(content).toPropertyWhenPresent("content", record::getContent)
            .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
            .map(uid).toPropertyWhenPresent("uid", record::getUid)
            .map(vote).toPropertyWhenPresent("vote", record::getVote)
            .map(refPostId).toPropertyWhenPresent("refPostId", record::getRefPostId)
            .map(refReplyId).toPropertyWhenPresent("refReplyId", record::getRefReplyId)
            .map(topped).toPropertyWhenPresent("topped", record::getTopped)
            .map(deleteTime).toPropertyWhenPresent("deleteTime", record::getDeleteTime)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    default Optional<Reply> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, reply, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    default List<Reply> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, reply, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    default List<Reply> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, reply, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    default Optional<Reply> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, reply, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    static UpdateDSL<UpdateModel> updateAllColumns(Reply record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(content).equalTo(record::getContent)
                .set(createTime).equalTo(record::getCreateTime)
                .set(uid).equalTo(record::getUid)
                .set(vote).equalTo(record::getVote)
                .set(refPostId).equalTo(record::getRefPostId)
                .set(refReplyId).equalTo(record::getRefReplyId)
                .set(topped).equalTo(record::getTopped)
                .set(deleteTime).equalTo(record::getDeleteTime);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Reply record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(content).equalToWhenPresent(record::getContent)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(uid).equalToWhenPresent(record::getUid)
                .set(vote).equalToWhenPresent(record::getVote)
                .set(refPostId).equalToWhenPresent(record::getRefPostId)
                .set(refReplyId).equalToWhenPresent(record::getRefReplyId)
                .set(topped).equalToWhenPresent(record::getTopped)
                .set(deleteTime).equalToWhenPresent(record::getDeleteTime);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    default int updateByPrimaryKey(Reply record) {
        return update(c ->
            c.set(content).equalTo(record::getContent)
            .set(createTime).equalTo(record::getCreateTime)
            .set(uid).equalTo(record::getUid)
            .set(vote).equalTo(record::getVote)
            .set(refPostId).equalTo(record::getRefPostId)
            .set(refReplyId).equalTo(record::getRefReplyId)
            .set(topped).equalTo(record::getTopped)
            .set(deleteTime).equalTo(record::getDeleteTime)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.reply")
    default int updateByPrimaryKeySelective(Reply record) {
        return update(c ->
            c.set(content).equalToWhenPresent(record::getContent)
            .set(createTime).equalToWhenPresent(record::getCreateTime)
            .set(uid).equalToWhenPresent(record::getUid)
            .set(vote).equalToWhenPresent(record::getVote)
            .set(refPostId).equalToWhenPresent(record::getRefPostId)
            .set(refReplyId).equalToWhenPresent(record::getRefReplyId)
            .set(topped).equalToWhenPresent(record::getTopped)
            .set(deleteTime).equalToWhenPresent(record::getDeleteTime)
            .where(id, isEqualTo(record::getId))
        );
    }
}