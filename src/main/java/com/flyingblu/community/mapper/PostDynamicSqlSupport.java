package com.flyingblu.community.mapper;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class PostDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    public static final Post post = new Post();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source field: community.post.id")
    public static final SqlColumn<Integer> id = post.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source field: community.post.uid")
    public static final SqlColumn<String> uid = post.uid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source field: community.post.create_time")
    public static final SqlColumn<Date> createTime = post.createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source field: community.post.title")
    public static final SqlColumn<String> title = post.title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source field: community.post.body")
    public static final SqlColumn<String> body = post.body;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source field: community.post.vote")
    public static final SqlColumn<Integer> vote = post.vote;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source field: community.post.community_id")
    public static final SqlColumn<Integer> communityId = post.communityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source field: community.post.topped")
    public static final SqlColumn<Boolean> topped = post.topped;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source field: community.post.best")
    public static final SqlColumn<Boolean> best = post.best;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source field: community.post.delete_time")
    public static final SqlColumn<Date> deleteTime = post.deleteTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1979796+08:00", comments="Source Table: community.post")
    public static final class Post extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> uid = column("uid", JDBCType.VARCHAR);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> title = column("title", JDBCType.VARCHAR);

        public final SqlColumn<String> body = column("body", JDBCType.VARCHAR);

        public final SqlColumn<Integer> vote = column("vote", JDBCType.INTEGER);

        public final SqlColumn<Integer> communityId = column("community_id", JDBCType.INTEGER);

        public final SqlColumn<Boolean> topped = column("topped", JDBCType.BIT);

        public final SqlColumn<Boolean> best = column("best", JDBCType.BIT);

        public final SqlColumn<Date> deleteTime = column("delete_time", JDBCType.TIMESTAMP);

        public Post() {
            super("community.post");
        }
    }
}