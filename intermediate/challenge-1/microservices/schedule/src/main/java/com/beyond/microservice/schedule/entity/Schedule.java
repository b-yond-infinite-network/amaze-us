package com.beyond.microservice.schedule.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.beyond.microservice.schedule.bus.Bus;
import com.beyond.microservice.schedule.driver.Driver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="SCHEDULETABLE")
@Getter
@Setter
@RequiredArgsConstructor
public class Schedule {
    
    @Id
    @Column(name = "SCHEDULE_ID", nullable = false)
    private Long scheduleId;
    
    @ManyToOne
    @JoinColumn(name = "BUS_ID")
    private Bus bus;
    
    @ManyToOne
    @JoinColumn(name = "DRIVER_NAME")
    private Driver driver;
   
}
