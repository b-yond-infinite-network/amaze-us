package com.audela.challenge.busapi.repository;

import com.audela.challenge.busapi.entity.BusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends CrudRepository<BusEntity, Integer> {

}
