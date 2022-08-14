package com.beyond.microservice.driver.repository;

import com.beyond.microservice.driver.entity.Driver;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends PagingAndSortingRepository<Driver, Long> {
}
