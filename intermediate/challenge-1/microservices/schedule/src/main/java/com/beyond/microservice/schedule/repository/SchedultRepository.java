package com.beyond.microservice.schedule.repository;

import java.util.List;

import com.beyond.microservice.schedule.entity.Schedule;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedultRepository extends PagingAndSortingRepository<Schedule, Long>{
    
    List<Schedule> findByBusId(Long busId);
    
    List<Schedule> findByDriverId(Long busId);
}
