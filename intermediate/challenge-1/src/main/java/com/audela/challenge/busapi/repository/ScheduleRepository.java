package com.audela.challenge.busapi.repository;

import com.audela.challenge.busapi.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<ScheduleEntity, Integer> {

    @Query("select a from ScheduleEntity a where a.driver.id = :driverId and a.eta > :fromDate and a.etd < :toDate")
    List<ScheduleEntity> getScheduleByDriverIdWithinDateRange(int driverId, OffsetDateTime fromDate, OffsetDateTime toDate);

    @Query("select a from ScheduleEntity a where a.bus.id = :busId and a.eta > :fromDate and a.etd < :toDate")
    List<ScheduleEntity> getScheduleByBusIdWithinDateRange(int busId, OffsetDateTime fromDate, OffsetDateTime toDate);

    @Transactional
    @Modifying
    @Query("update ScheduleEntity a set a.bus.id = :bus_id, a.driver.id = :driver_id, " +
            "a.startStation = :start_station, a.destinationStation = :destination_station, " +
            "a.etd = :etd, a.eta = :eta, a.atd = :atd, a.ata = :ata where a.id = :id")
    int updateSchedule(@Param(value = "id") int id,
                       @Param(value = "bus_id") int bus_id,
                       @Param(value = "driver_id") int driver_id,
                       @Param(value = "start_station") String start_station,
                       @Param(value = "destination_station") String destination_station,
                       @Param(value = "etd") OffsetDateTime etd,
                       @Param(value = "eta") OffsetDateTime eta,
                       @Param(value = "atd") OffsetDateTime atd,
                       @Param(value = "ata") OffsetDateTime ata);

    @Query("select a from ScheduleEntity a where a.eta > :fromDate and a.etd < :toDate")
    List<ScheduleEntity> getScheduleWithinDateRange(OffsetDateTime fromDate, OffsetDateTime toDate);
}
