package io.xdea.xmux.forum.model;

import java.util.Date;

public class Moderate {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.moderate.id
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.moderate.uid
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    private String uid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.moderate.group_id
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    private Integer groupId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.moderate.create_time
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.moderate.delete_time
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    private Date deleteTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.moderate.id
     *
     * @return the value of forum.moderate.id
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.moderate
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public Moderate withId(Integer id) {
        this.setId(id);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.moderate.id
     *
     * @param id the value for forum.moderate.id
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.moderate.uid
     *
     * @return the value of forum.moderate.uid
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.moderate
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public Moderate withUid(String uid) {
        this.setUid(uid);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.moderate.uid
     *
     * @param uid the value for forum.moderate.uid
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.moderate.group_id
     *
     * @return the value of forum.moderate.group_id
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.moderate
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public Moderate withGroupId(Integer groupId) {
        this.setGroupId(groupId);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.moderate.group_id
     *
     * @param groupId the value for forum.moderate.group_id
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.moderate.create_time
     *
     * @return the value of forum.moderate.create_time
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.moderate
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public Moderate withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.moderate.create_time
     *
     * @param createTime the value for forum.moderate.create_time
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.moderate.delete_time
     *
     * @return the value of forum.moderate.delete_time
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.moderate
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public Moderate withDeleteTime(Date deleteTime) {
        this.setDeleteTime(deleteTime);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.moderate.delete_time
     *
     * @param deleteTime the value for forum.moderate.delete_time
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.moderate
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", groupId=").append(groupId);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append("]");
        return sb.toString();
    }
}