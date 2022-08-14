package com.beyond.micorservice.driver.repository;

import com.beyond.micorservice.driver.entity.Driver;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends PagingAndSortingRepository<Driver, Long> {
}
