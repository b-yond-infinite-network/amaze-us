package com.beyond.microservice.bus.driver;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface DriverRepository extends PagingAndSortingRepository<Driver, Long> {
    
    List<Driver> findByName(@Param("name") String name);
}
