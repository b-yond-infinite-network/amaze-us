package com.audela.challenge.busapi.repository;

import com.audela.challenge.busapi.entity.DriverEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends CrudRepository<DriverEntity, Integer> {

}
