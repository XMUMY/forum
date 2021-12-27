package com.flyingblu.community.model;

import java.util.Date;
import javax.annotation.Generated;

public class Member {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.member.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.member.uid")
    private String uid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.member.community_id")
    private Integer communityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.member.create_time")
    private Date createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.member.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.member.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.member.uid")
    public String getUid() {
        return uid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.member.uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.member.community_id")
    public Integer getCommunityId() {
        return communityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.member.community_id")
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.member.create_time")
    public Date getCreateTime() {
        return createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.member.create_time")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}