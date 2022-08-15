package com.beyond.microservice.schedule.bus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.beyond.microservice.schedule.driver.Driver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class Bus {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    
    @OneToOne
    private Driver driver;
}
