package com.audela.challenge.busapi.entity;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;


@Data
@Table(name="SCHEDULE")
@Entity
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Integer id;

    @Column(name="start_station",nullable = false, length = 70)
    private String startStation;

    @Column(name="destination_station",nullable = false, length = 70)
    private String destinationStation;

    @Column(name="etd" ,nullable = false)
    private OffsetDateTime etd;

    @Column(name="eta" ,nullable = false)
    private OffsetDateTime eta;

    @Column(name="atd")
    private OffsetDateTime atd;

    @Column(name="ata")
    private OffsetDateTime ata;

}