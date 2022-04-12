package com.audela.challenge.busapi.repository;

import com.audela.challenge.busapi.entity.BusEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BusRepository extends CrudRepository<BusEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update BusEntity a set a.capacity = :capacity, a.model = :model, " +
            "a.make = :make where a.id = :id")
    int updateBus(@Param(value = "id") int id,
                     @Param(value = "capacity") int capacity,
                     @Param(value = "model") String model,
                     @Param(value = "make") String make);
}
