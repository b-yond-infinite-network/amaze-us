package com.audela.challenge.busapi.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.OffsetDateTime;

@Getter
@Setter
public class DriverScheduleVo {

    private int driverId;
    private String firstName;
    private String lastName;

    private int busId;
    private int capacity;
    private String model;

    private int scheduleId;
    private String startStation;
    private String destinationStation;
    private OffsetDateTime etd;
    private OffsetDateTime eta;

}
