package com.audela.challenge.busapi.repository;

import com.audela.challenge.busapi.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<ScheduleEntity, Integer> {

    @Query("select a from ScheduleEntity a where a.driver.id = :driverId and a.eta > :fromDate and a.etd < :toDate")
    List<ScheduleEntity> getScheduleByDriverIdWithinDateRange(int driverId, OffsetDateTime fromDate, OffsetDateTime toDate);

    @Query("select a from ScheduleEntity a where a.bus.id = :busId and a.eta > :fromDate and a.etd < :toDate")
    List<ScheduleEntity> getScheduleByBusIdWithinDateRange(int busId, OffsetDateTime fromDate, OffsetDateTime toDate);
}
