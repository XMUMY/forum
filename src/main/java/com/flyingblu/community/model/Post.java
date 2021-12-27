package com.flyingblu.community.model;

import java.util.Date;
import javax.annotation.Generated;

public class Post {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.uid")
    private String uid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.create_time")
    private Date createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.title")
    private String title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.body")
    private String body;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.vote")
    private Integer vote;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.community_id")
    private Integer communityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.topped")
    private Boolean topped;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.best")
    private Boolean best;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.delete_time")
    private Date deleteTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.uid")
    public String getUid() {
        return uid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.create_time")
    public Date getCreateTime() {
        return createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.create_time")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.title")
    public String getTitle() {
        return title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.title")
    public void setTitle(String title) {
        this.title = title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.body")
    public String getBody() {
        return body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.body")
    public void setBody(String body) {
        this.body = body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.vote")
    public Integer getVote() {
        return vote;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.vote")
    public void setVote(Integer vote) {
        this.vote = vote;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.community_id")
    public Integer getCommunityId() {
        return communityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.community_id")
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.topped")
    public Boolean getTopped() {
        return topped;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.topped")
    public void setTopped(Boolean topped) {
        this.topped = topped;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.best")
    public Boolean getBest() {
        return best;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.best")
    public void setBest(Boolean best) {
        this.best = best;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.delete_time")
    public Date getDeleteTime() {
        return deleteTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-27T16:19:13.6828025+08:00", comments="Source field: community.post.delete_time")
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}