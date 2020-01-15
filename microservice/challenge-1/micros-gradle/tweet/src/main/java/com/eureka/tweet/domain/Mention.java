package com.eureka.tweet.domain;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;
import java.util.UUID;


/**
 * Mention entity in canssandra.
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@Table(keyspace = "tweets", name = "mentions",
        readConsistency = "QUORUM",
        writeConsistency = "QUORUM",
        caseSensitiveKeyspace = false,
        caseSensitiveTable = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mention {

    @Column(name = "post_ids")
    private Set<UUID> postIds;
    @PartitionKey(0)
    @Column(name = "user_id")
    private Long userId;

    public Set<UUID> getPostIds() {
        return postIds;
    }

    public void setPostIds(Set<UUID> postIds) {
        this.postIds = postIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
