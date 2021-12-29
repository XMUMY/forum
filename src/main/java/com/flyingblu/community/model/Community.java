package com.flyingblu.community.model;

import java.util.Date;
import javax.annotation.Generated;

public class Community {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6750803+08:00", comments="Source field: community.community.id")
    private Integer id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6780873+08:00", comments="Source field: community.community.title")
    private String title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6780873+08:00", comments="Source field: community.community.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6780873+08:00", comments="Source field: community.community.create_time")
    private Date createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6790874+08:00", comments="Source field: community.community.delete_time")
    private Date deleteTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6780873+08:00", comments="Source field: community.community.id")
    public Integer getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6780873+08:00", comments="Source Table: community.community")
    public Community withId(Integer id) {
        this.setId(id);
        return this;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6780873+08:00", comments="Source field: community.community.id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6780873+08:00", comments="Source field: community.community.title")
    public String getTitle() {
        return title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6780873+08:00", comments="Source Table: community.community")
    public Community withTitle(String title) {
        this.setTitle(title);
        return this;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6780873+08:00", comments="Source field: community.community.title")
    public void setTitle(String title) {
        this.title = title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6780873+08:00", comments="Source field: community.community.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6780873+08:00", comments="Source Table: community.community")
    public Community withDescription(String description) {
        this.setDescription(description);
        return this;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6780873+08:00", comments="Source field: community.community.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6780873+08:00", comments="Source field: community.community.create_time")
    public Date getCreateTime() {
        return createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6790874+08:00", comments="Source Table: community.community")
    public Community withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6780873+08:00", comments="Source field: community.community.create_time")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6790874+08:00", comments="Source field: community.community.delete_time")
    public Date getDeleteTime() {
        return deleteTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6790874+08:00", comments="Source Table: community.community")
    public Community withDeleteTime(Date deleteTime) {
        this.setDeleteTime(deleteTime);
        return this;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6790874+08:00", comments="Source field: community.community.delete_time")
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2021-12-29T13:35:57.6790874+08:00", comments="Source Table: community.community")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append("]");
        return sb.toString();
    }
}