package com.flyingblu.community.mapper;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class CommunityDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.7699718+08:00", comments="Source Table: community.community")
    public static final Community community = new Community();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.7699718+08:00", comments="Source field: community.community.id")
    public static final SqlColumn<Integer> id = community.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.7699718+08:00", comments="Source field: community.community.title")
    public static final SqlColumn<String> title = community.title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.7699718+08:00", comments="Source field: community.community.description")
    public static final SqlColumn<String> description = community.description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.7699718+08:00", comments="Source field: community.community.create_time")
    public static final SqlColumn<Date> createTime = community.createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.7699718+08:00", comments="Source field: community.community.delete_time")
    public static final SqlColumn<Date> deleteTime = community.deleteTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.7699718+08:00", comments="Source Table: community.community")
    public static final class Community extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> title = column("title", JDBCType.VARCHAR);

        public final SqlColumn<String> description = column("description", JDBCType.VARCHAR);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> deleteTime = column("delete_time", JDBCType.TIMESTAMP);

        public Community() {
            super("community.community");
        }
    }
}