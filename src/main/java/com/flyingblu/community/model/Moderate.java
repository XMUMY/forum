package com.flyingblu.community.model;

import java.util.Date;
import javax.annotation.Generated;

public class Moderate {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.uid")
    private String uid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.community_id")
    private Integer communityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.create_time")
    private Date createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.delete_time")
    private Date deleteTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source Table: community.moderate")
    public Moderate withId(Integer id) {
        this.setId(id);
        return this;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.uid")
    public String getUid() {
        return uid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source Table: community.moderate")
    public Moderate withUid(String uid) {
        this.setUid(uid);
        return this;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.community_id")
    public Integer getCommunityId() {
        return communityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source Table: community.moderate")
    public Moderate withCommunityId(Integer communityId) {
        this.setCommunityId(communityId);
        return this;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.community_id")
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.create_time")
    public Date getCreateTime() {
        return createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source Table: community.moderate")
    public Moderate withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.create_time")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.delete_time")
    public Date getDeleteTime() {
        return deleteTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source Table: community.moderate")
    public Moderate withDeleteTime(Date deleteTime) {
        this.setDeleteTime(deleteTime);
        return this;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source field: community.moderate.delete_time")
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T20:45:56.2057046+08:00", comments="Source Table: community.moderate")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", communityId=").append(communityId);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append("]");
        return sb.toString();
    }
}