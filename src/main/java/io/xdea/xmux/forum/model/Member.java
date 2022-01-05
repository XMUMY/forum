package io.xdea.xmux.forum.model;

import java.util.Date;

public class Member {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.member.id
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.member.uid
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    private String uid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.member.group_id
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    private Integer groupId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.member.create_time
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.member.id
     *
     * @return the value of forum.member.id
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.member
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    public Member withId(Integer id) {
        this.setId(id);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.member.id
     *
     * @param id the value for forum.member.id
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.member.uid
     *
     * @return the value of forum.member.uid
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.member
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    public Member withUid(String uid) {
        this.setUid(uid);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.member.uid
     *
     * @param uid the value for forum.member.uid
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.member.group_id
     *
     * @return the value of forum.member.group_id
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.member
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    public Member withGroupId(Integer groupId) {
        this.setGroupId(groupId);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.member.group_id
     *
     * @param groupId the value for forum.member.group_id
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.member.create_time
     *
     * @return the value of forum.member.create_time
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.member
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    public Member withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.member.create_time
     *
     * @param createTime the value for forum.member.create_time
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.member
     *
     * @mbg.generated Wed Jan 05 21:54:23 CST 2022
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
        sb.append("]");
        return sb.toString();
    }
}