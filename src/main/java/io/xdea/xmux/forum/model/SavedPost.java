package io.xdea.xmux.forum.model;

import java.util.Date;

public class SavedPost {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.saved_post.id
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.saved_post.uid
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    private String uid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.saved_post.post_id
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    private Integer postId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.saved_post.create_at
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    private Date createAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.saved_post.id
     *
     * @return the value of forum.saved_post.id
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_post
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
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
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
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
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_post
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
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
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
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
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    public Integer getPostId() {
        return postId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_post
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
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
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.saved_post.create_at
     *
     * @return the value of forum.saved_post.create_at
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_post
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    public SavedPost withCreateAt(Date createAt) {
        this.setCreateAt(createAt);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.saved_post.create_at
     *
     * @param createAt the value for forum.saved_post.create_at
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_post
     *
     * @mbg.generated Thu Nov 07 10:00:12 CST 2024
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
        sb.append(", createAt=").append(createAt);
        sb.append("]");
        return sb.toString();
    }
}