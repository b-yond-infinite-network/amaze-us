package com.eureka.social.domain;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

/**
 * Following compound pk.
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
public class FollowingId implements Serializable {

    @Column(name = "source_id")
    private Long sourceId;

    @Column(name = "destination_id")
    private Long destinationId;

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

    public FollowingId() {
    }

    public FollowingId(Long sourceId, Long destinationId) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowingId that = (FollowingId) o;
        return Objects.equals(sourceId, that.sourceId) &&
                Objects.equals(destinationId, that.destinationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, destinationId);
    }
}
