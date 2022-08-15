package com.beyond.microservice.schedule.model;

import java.time.DayOfWeek;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleDto {
    private Long id;
    
    private Long busId;
    
    private String driverName;
    
    
    private DayOfWeek day;
    
    private Date start;
    
    private Date end;
    
}
