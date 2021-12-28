package com.flyingblu.community.model;

import java.util.Date;
import javax.annotation.Generated;

public class Moderate {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.uid")
    private String uid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.community_id")
    private Integer communityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.create_time")
    private Date createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.delete_time")
    private Date deleteTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.uid")
    public String getUid() {
        return uid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.community_id")
    public Integer getCommunityId() {
        return communityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.community_id")
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.create_time")
    public Date getCreateTime() {
        return createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.create_time")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.delete_time")
    public Date getDeleteTime() {
        return deleteTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T18:18:31.1929742+08:00", comments="Source field: community.moderate.delete_time")
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}