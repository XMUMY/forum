package com.flyingblu.community.model;

import java.util.Date;
import javax.annotation.Generated;

public class Reply {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.content")
    private String content;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.create_time")
    private Date createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.uid")
    private String uid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.vote")
    private Integer vote;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.ref_post_id")
    private Integer refPostId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.ref_reply_id")
    private Integer refReplyId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.topped")
    private Boolean topped;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.delete_time")
    private Date deleteTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.content")
    public String getContent() {
        return content;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.content")
    public void setContent(String content) {
        this.content = content;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.create_time")
    public Date getCreateTime() {
        return createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.create_time")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.uid")
    public String getUid() {
        return uid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.vote")
    public Integer getVote() {
        return vote;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.vote")
    public void setVote(Integer vote) {
        this.vote = vote;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.ref_post_id")
    public Integer getRefPostId() {
        return refPostId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.ref_post_id")
    public void setRefPostId(Integer refPostId) {
        this.refPostId = refPostId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.ref_reply_id")
    public Integer getRefReplyId() {
        return refReplyId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.ref_reply_id")
    public void setRefReplyId(Integer refReplyId) {
        this.refReplyId = refReplyId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.topped")
    public Boolean getTopped() {
        return topped;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.topped")
    public void setTopped(Boolean topped) {
        this.topped = topped;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.delete_time")
    public Date getDeleteTime() {
        return deleteTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-28T14:23:04.801191+08:00", comments="Source field: community.reply.delete_time")
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}