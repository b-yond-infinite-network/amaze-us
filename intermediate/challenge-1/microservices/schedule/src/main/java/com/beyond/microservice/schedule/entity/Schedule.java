package com.beyond.microservice.schedule.entity;

import java.time.DayOfWeek;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    @Column(name = "ID", nullable = false)
    private Long id;
    
    @ManyToOne
    @Column(name="BUS_ID", nullable = false)
    private Bus bus;
    
    @ManyToOne
    @Column(name="DRIVER_ID", nullable = false)
    private Driver driver;
    
    @Column(nullable = false)
    private DayOfWeek day;
    
    @Column(nullable = false)
    private Date start;
    
    @Column(nullable = false)
   private Date end;
}
