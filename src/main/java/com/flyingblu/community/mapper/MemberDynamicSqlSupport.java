package com.flyingblu.community.mapper;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class MemberDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.member")
    public static final Member member = new Member();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.member.id")
    public static final SqlColumn<Integer> id = member.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.member.uid")
    public static final SqlColumn<String> uid = member.uid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.member.community_id")
    public static final SqlColumn<Integer> communityId = member.communityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.member.create_time")
    public static final SqlColumn<Date> createTime = member.createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source Table: community.member")
    public static final class Member extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> uid = column("uid", JDBCType.VARCHAR);

        public final SqlColumn<Integer> communityId = column("community_id", JDBCType.INTEGER);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public Member() {
            super("community.member");
        }
    }
}