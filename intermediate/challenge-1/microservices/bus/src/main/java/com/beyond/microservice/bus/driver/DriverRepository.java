package com.beyond.microservice.bus.driver;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface DriverRepository extends PagingAndSortingRepository<Driver, Long> {

}
