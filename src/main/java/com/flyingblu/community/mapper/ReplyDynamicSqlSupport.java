package com.flyingblu.community.mapper;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class ReplyDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6928682+08:00", comments="Source Table: community.reply")
    public static final Reply reply = new Reply();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6928682+08:00", comments="Source field: community.reply.id")
    public static final SqlColumn<Integer> id = reply.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6928682+08:00", comments="Source field: community.reply.content")
    public static final SqlColumn<String> content = reply.content;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6928682+08:00", comments="Source field: community.reply.create_time")
    public static final SqlColumn<Date> createTime = reply.createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6928682+08:00", comments="Source field: community.reply.uid")
    public static final SqlColumn<String> uid = reply.uid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6928682+08:00", comments="Source field: community.reply.vote")
    public static final SqlColumn<Integer> vote = reply.vote;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6928682+08:00", comments="Source field: community.reply.ref_post_id")
    public static final SqlColumn<Integer> refPostId = reply.refPostId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6928682+08:00", comments="Source field: community.reply.ref_reply_id")
    public static final SqlColumn<Integer> refReplyId = reply.refReplyId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6928682+08:00", comments="Source field: community.reply.topped")
    public static final SqlColumn<Boolean> topped = reply.topped;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6928682+08:00", comments="Source field: community.reply.delete_time")
    public static final SqlColumn<Date> deleteTime = reply.deleteTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6928682+08:00", comments="Source Table: community.reply")
    public static final class Reply extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> content = column("content", JDBCType.VARCHAR);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> uid = column("uid", JDBCType.VARCHAR);

        public final SqlColumn<Integer> vote = column("vote", JDBCType.INTEGER);

        public final SqlColumn<Integer> refPostId = column("ref_post_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> refReplyId = column("ref_reply_id", JDBCType.INTEGER);

        public final SqlColumn<Boolean> topped = column("topped", JDBCType.BIT);

        public final SqlColumn<Date> deleteTime = column("delete_time", JDBCType.TIMESTAMP);

        public Reply() {
            super("community.reply");
        }
    }
}