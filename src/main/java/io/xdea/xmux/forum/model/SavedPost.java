package io.xdea.xmux.forum.model;

import java.util.Date;

public class SavedPost {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.saved_post.id
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.saved_post.uid
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private String uid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.saved_post.post_id
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private Integer postId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.saved_post.create_time
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.saved_post.id
     *
     * @return the value of forum.saved_post.id
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public SavedPost withId(Integer id) {
        this.setId(id);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.saved_post.id
     *
     * @param id the value for forum.saved_post.id
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.saved_post.uid
     *
     * @return the value of forum.saved_post.uid
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public SavedPost withUid(String uid) {
        this.setUid(uid);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.saved_post.uid
     *
     * @param uid the value for forum.saved_post.uid
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.saved_post.post_id
     *
     * @return the value of forum.saved_post.post_id
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Integer getPostId() {
        return postId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public SavedPost withPostId(Integer postId) {
        this.setPostId(postId);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.saved_post.post_id
     *
     * @param postId the value for forum.saved_post.post_id
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.saved_post.create_time
     *
     * @return the value of forum.saved_post.create_time
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_post
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public SavedPost withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.saved_post.create_time
     *
     * @param createTime the value for forum.saved_post.create_time
     *
     * @mbg.generated Mon Jan 17 16:32:59 CST 2022
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_post
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
        sb.append(", postId=").append(postId);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}