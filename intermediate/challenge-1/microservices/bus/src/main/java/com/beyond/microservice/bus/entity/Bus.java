package com.beyond.microservice.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.beyond.microservice.bus.driver.Driver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name ="BUSTABLE")
@Getter
@Setter
@Builder
public class Bus {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private Integer capacity;
    private String model;
    private String maker;
    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
}
