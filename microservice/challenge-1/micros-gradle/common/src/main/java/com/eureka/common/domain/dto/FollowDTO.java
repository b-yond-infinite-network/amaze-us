package com.eureka.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class FollowDTO {

    public Long sourceId;
    public Long destinationId;
    private LocalDateTime updatedAt;

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "FollowDTO{" +
                "sourceId=" + sourceId +
                ", destinationId=" + destinationId +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
