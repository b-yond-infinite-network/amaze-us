package com.eureka.social.domain;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class FollowersId implements Serializable {

    @Column(name = "source_id")
    private Long sourceId;

    @Column(name = "destination_id")
    private Long destinationId;

    public FollowersId() {
    }

    public FollowersId(Long sourceId, Long destinationId) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowersId that = (FollowersId) o;
        return Objects.equals(sourceId, that.sourceId) &&
                Objects.equals(destinationId, that.destinationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, destinationId);
    }
}
