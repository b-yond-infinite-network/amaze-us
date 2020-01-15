package com.eureka.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;


/**
 * Tweet DTO
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
public class TweetDTO {

    private UUID id;
    @JsonProperty("user_id")
    private Long userId;
    private String content;
    private Long timestamp;
    private UUID replyPid;
    private Long replyUid;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getReplyPid() {
        return replyPid;
    }

    public void setReplyPid(UUID replyPid) {
        this.replyPid = replyPid;
    }

    public Long getReplyUid() {
        return replyUid;
    }

    public void setReplyUid(Long replyUid) {
        this.replyUid = replyUid;
    }


    @Override
    public String toString() {
        return "TweetDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", replyPid=" + replyPid +
                ", replyUid=" + replyUid +
                '}';
    }
}