package com.eureka.tweet.domain;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;


@Table(keyspace = "tweets", name = "tweets",
        readConsistency = "QUORUM",
        writeConsistency = "QUORUM",
        caseSensitiveKeyspace = false,
        caseSensitiveTable = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tweet {

    @PartitionKey(0)
    private UUID id;
    @Column(name = "user_id")
    @JsonProperty("user_id")
    private Long userId;
    private String content;
    @Column(name = "created_at")
    private Long timestamp;
    @Column(name = "reply_pid")
    private UUID replyPid;
    @Column(name = "reply_uid")
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
}
