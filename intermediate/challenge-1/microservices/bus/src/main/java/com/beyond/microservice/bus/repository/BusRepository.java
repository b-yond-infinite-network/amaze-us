package com.beyond.microservice.bus.repository;

import com.beyond.microservice.bus.entity.Bus;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BusRepository extends PagingAndSortingRepository<Bus, Long> {
}
