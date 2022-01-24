package io.xdea.xmux.forum.model;

import java.util.Date;

public class SavedReply {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.saved_reply.id
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.saved_reply.uid
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    private String uid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.saved_reply.reply_id
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    private Integer replyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column forum.saved_reply.create_time
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.saved_reply.id
     *
     * @return the value of forum.saved_reply.id
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_reply
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public SavedReply withId(Integer id) {
        this.setId(id);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.saved_reply.id
     *
     * @param id the value for forum.saved_reply.id
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.saved_reply.uid
     *
     * @return the value of forum.saved_reply.uid
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_reply
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public SavedReply withUid(String uid) {
        this.setUid(uid);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.saved_reply.uid
     *
     * @param uid the value for forum.saved_reply.uid
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.saved_reply.reply_id
     *
     * @return the value of forum.saved_reply.reply_id
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public Integer getReplyId() {
        return replyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_reply
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public SavedReply withReplyId(Integer replyId) {
        this.setReplyId(replyId);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.saved_reply.reply_id
     *
     * @param replyId the value for forum.saved_reply.reply_id
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column forum.saved_reply.create_time
     *
     * @return the value of forum.saved_reply.create_time
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_reply
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public SavedReply withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column forum.saved_reply.create_time
     *
     * @param createTime the value for forum.saved_reply.create_time
     *
     * @mbg.generated Mon Jan 24 18:57:53 CST 2022
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table forum.saved_reply
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
        sb.append(", replyId=").append(replyId);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}