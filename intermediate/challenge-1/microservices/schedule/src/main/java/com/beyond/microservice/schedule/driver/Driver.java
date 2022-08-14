package com.beyond.microservice.schedule.driver;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Driver {
    
    @Id
    @Column(name = "driver_id", nullable = false)
    private Long driverId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String firstname;
    
}
