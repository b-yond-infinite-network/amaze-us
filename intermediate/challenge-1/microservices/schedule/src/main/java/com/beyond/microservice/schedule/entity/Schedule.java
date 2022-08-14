package com.beyond.microservice.schedule.entity;

import java.time.DayOfWeek;
import java.util.Date;
import javax.persistence.Column;
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
    private Long id;
    
    @ManyToOne
    private Bus bus;
    
    @ManyToOne
    private Driver driver;
    
    private DayOfWeek day;
    
    private Date start;
    
   private Date end;
}
