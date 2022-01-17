package io.xdea.xmux.forum.model;

import java.util.Date;

public class Post {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.post.id
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.post.uid
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private String uid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.post.create_time
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.post.update_time
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.post.delete_time
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private Date deleteTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.post.title
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.post.body
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private String body;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.post.vote
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private Integer vote;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.post.group_id
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private Integer groupId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.post.topped
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private Boolean topped;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.post.best
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private Boolean best;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.post.id
     *
     * @return the value of forum.post.id
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Post withId(Integer id) {
        this.setId(id);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.post.id
     *
     * @param id the value for forum.post.id
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.post.uid
     *
     * @return the value of forum.post.uid
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Post withUid(String uid) {
        this.setUid(uid);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.post.uid
     *
     * @param uid the value for forum.post.uid
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.post.create_time
     *
     * @return the value of forum.post.create_time
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Post withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.post.create_time
     *
     * @param createTime the value for forum.post.create_time
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.post.update_time
     *
     * @return the value of forum.post.update_time
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Post withUpdateTime(Date updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.post.update_time
     *
     * @param updateTime the value for forum.post.update_time
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.post.delete_time
     *
     * @return the value of forum.post.delete_time
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Post withDeleteTime(Date deleteTime) {
        this.setDeleteTime(deleteTime);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.post.delete_time
     *
     * @param deleteTime the value for forum.post.delete_time
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.post.title
     *
     * @return the value of forum.post.title
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Post withTitle(String title) {
        this.setTitle(title);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.post.title
     *
     * @param title the value for forum.post.title
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.post.body
     *
     * @return the value of forum.post.body
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public String getBody() {
        return body;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Post withBody(String body) {
        this.setBody(body);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.post.body
     *
     * @param body the value for forum.post.body
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.post.vote
     *
     * @return the value of forum.post.vote
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Integer getVote() {
        return vote;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Post withVote(Integer vote) {
        this.setVote(vote);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.post.vote
     *
     * @param vote the value for forum.post.vote
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setVote(Integer vote) {
        this.vote = vote;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.post.group_id
     *
     * @return the value of forum.post.group_id
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Post withGroupId(Integer groupId) {
        this.setGroupId(groupId);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.post.group_id
     *
     * @param groupId the value for forum.post.group_id
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.post.topped
     *
     * @return the value of forum.post.topped
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Boolean getTopped() {
        return topped;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Post withTopped(Boolean topped) {
        this.setTopped(topped);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.post.topped
     *
     * @param topped the value for forum.post.topped
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setTopped(Boolean topped) {
        this.topped = topped;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.post.best
     *
     * @return the value of forum.post.best
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Boolean getBest() {
        return best;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Post withBest(Boolean best) {
        this.setBest(best);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.post.best
     *
     * @param best the value for forum.post.best
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setBest(Boolean best) {
        this.best = best;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append(", title=").append(title);
        sb.append(", body=").append(body);
        sb.append(", vote=").append(vote);
        sb.append(", groupId=").append(groupId);
        sb.append(", topped=").append(topped);
        sb.append(", best=").append(best);
        sb.append("]");
        return sb.toString();
    }
}