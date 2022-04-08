package com.audela.challenge.busapi.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ScheduleVo {
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
