package com.beyond.microservice.bus.repository;

import com.beyond.microservice.bus.entity.Bus;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends PagingAndSortingRepository<Bus, Long> {
}
