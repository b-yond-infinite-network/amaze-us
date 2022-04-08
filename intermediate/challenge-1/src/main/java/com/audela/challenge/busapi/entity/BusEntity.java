package com.audela.challenge.busapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * A bus has a capacity, a model, make, and an associated driver.
 */
@Data
@Table(name="BUS")
@Entity
public class BusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Integer id;

    @Column(name="capacity",nullable = false)
    private Integer capacity;

    @Column(name="model",nullable = false, length = 70)
    private String model;

    @Column(name="make",nullable = false, length = 70)
    private String make;

}
