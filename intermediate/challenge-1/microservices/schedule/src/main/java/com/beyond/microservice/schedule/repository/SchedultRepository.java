package com.beyond.microservice.schedule.repository;

import com.beyond.microservice.schedule.entity.Schedule;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface SchedultRepository extends PagingAndSortingRepository<Schedule, Long>{

}
