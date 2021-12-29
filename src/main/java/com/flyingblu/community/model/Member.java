package com.flyingblu.community.model;

import java.util.Date;
import javax.annotation.Generated;

public class Member {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7001489+08:00", comments="Source field: community.member.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7001489+08:00", comments="Source field: community.member.uid")
    private String uid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7006784+08:00", comments="Source field: community.member.community_id")
    private Integer communityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7006784+08:00", comments="Source field: community.member.create_time")
    private Date createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7001489+08:00", comments="Source field: community.member.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7001489+08:00", comments="Source Table: community.member")
    public Member withId(Integer id) {
        this.setId(id);
        return this;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7001489+08:00", comments="Source field: community.member.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7001489+08:00", comments="Source field: community.member.uid")
    public String getUid() {
        return uid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7006784+08:00", comments="Source Table: community.member")
    public Member withUid(String uid) {
        this.setUid(uid);
        return this;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7001489+08:00", comments="Source field: community.member.uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7006784+08:00", comments="Source field: community.member.community_id")
    public Integer getCommunityId() {
        return communityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7006784+08:00", comments="Source Table: community.member")
    public Member withCommunityId(Integer communityId) {
        this.setCommunityId(communityId);
        return this;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7006784+08:00", comments="Source field: community.member.community_id")
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7006784+08:00", comments="Source field: community.member.create_time")
    public Date getCreateTime() {
        return createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7006784+08:00", comments="Source Table: community.member")
    public Member withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7006784+08:00", comments="Source field: community.member.create_time")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.7006784+08:00", comments="Source Table: community.member")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", communityId=").append(communityId);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}