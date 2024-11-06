package io.xdea.xmux.forum.model;

import java.util.Date;

public class Member {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.member.id
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.member.uid
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    private String uid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.member.forum_id
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    private Integer forumId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.member.create_at
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    private Date createAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.member.id
     *
     * @return the value of forum.member.id
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.member
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
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
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
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
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.member
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
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
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.member.forum_id
     *
     * @return the value of forum.member.forum_id
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    public Integer getForumId() {
        return forumId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.member
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    public Member withForumId(Integer forumId) {
        this.setForumId(forumId);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.member.forum_id
     *
     * @param forumId the value for forum.member.forum_id
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    public void setForumId(Integer forumId) {
        this.forumId = forumId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.member.create_at
     *
     * @return the value of forum.member.create_at
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.member
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    public Member withCreateAt(Date createAt) {
        this.setCreateAt(createAt);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.member.create_at
     *
     * @param createAt the value for forum.member.create_at
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.member
     *
     * @mbg.generated Wed Nov 06 17:40:56 CST 2024
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", forumId=").append(forumId);
        sb.append(", createAt=").append(createAt);
        sb.append("]");
        return sb.toString();
    }
}