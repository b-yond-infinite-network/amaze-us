package com.beyond.microservice.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name ="BUSTABLE")
@Getter
@Setter
public class Bus {
    
    @Id
    @Column(name = "bus_id", nullable = false)
    private Long busId;
    
    private int capacity;
    private String model;
    private String maker;

}
