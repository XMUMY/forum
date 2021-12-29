package com.flyingblu.community.mapper;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class ModerateDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7050003+08:00", comments="Source Table: community.moderate")
    public static final Moderate moderate = new Moderate();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7050003+08:00", comments="Source field: community.moderate.id")
    public static final SqlColumn<Integer> id = moderate.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7050003+08:00", comments="Source field: community.moderate.uid")
    public static final SqlColumn<String> uid = moderate.uid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7056682+08:00", comments="Source field: community.moderate.community_id")
    public static final SqlColumn<Integer> communityId = moderate.communityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7056682+08:00", comments="Source field: community.moderate.create_time")
    public static final SqlColumn<Date> createTime = moderate.createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7056682+08:00", comments="Source field: community.moderate.delete_time")
    public static final SqlColumn<Date> deleteTime = moderate.deleteTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7050003+08:00", comments="Source Table: community.moderate")
    public static final class Moderate extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> uid = column("uid", JDBCType.VARCHAR);

        public final SqlColumn<Integer> communityId = column("community_id", JDBCType.INTEGER);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> deleteTime = column("delete_time", JDBCType.TIMESTAMP);

        public Moderate() {
            super("community.moderate");
        }
    }
}