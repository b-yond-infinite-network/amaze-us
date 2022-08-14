package com.beyond.microservice.schedule.bus;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class Bus {
    @Id
    @Column(name = "bus_id", nullable = false)
    private Long busId;
    
    public void setBusId(Long busId) {
        this.busId = busId;
    }
}
