package com.audela.challenge.busapi.repository;

import com.audela.challenge.busapi.entity.ScheduleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends CrudRepository<ScheduleEntity, Integer> {

}
