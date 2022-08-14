package com.beyond.microservice.schedule.bus;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface BusRepository extends PagingAndSortingRepository<Bus, Long> {

}
