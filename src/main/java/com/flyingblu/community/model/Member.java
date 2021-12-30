package com.flyingblu.community.model;

import java.util.Date;

public class Member {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column community.member.id
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column community.member.uid
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    private String uid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column community.member.community_id
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    private Integer communityId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column community.member.create_time
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column community.member.id
     *
     * @return the value of community.member.id
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community.member
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    public Member withId(Integer id) {
        this.setId(id);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column community.member.id
     *
     * @param id the value for community.member.id
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column community.member.uid
     *
     * @return the value of community.member.uid
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community.member
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    public Member withUid(String uid) {
        this.setUid(uid);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column community.member.uid
     *
     * @param uid the value for community.member.uid
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column community.member.community_id
     *
     * @return the value of community.member.community_id
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    public Integer getCommunityId() {
        return communityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community.member
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    public Member withCommunityId(Integer communityId) {
        this.setCommunityId(communityId);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column community.member.community_id
     *
     * @param communityId the value for community.member.community_id
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column community.member.create_time
     *
     * @return the value of community.member.create_time
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community.member
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    public Member withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column community.member.create_time
     *
     * @param createTime the value for community.member.create_time
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table community.member
     *
     * @mbg.generated Thu Dec 30 16:05:56 CST 2021
     */
    @Override
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