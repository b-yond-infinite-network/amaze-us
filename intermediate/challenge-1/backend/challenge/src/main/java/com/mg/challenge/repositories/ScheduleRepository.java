package com.mg.challenge.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mg.challenge.pojos.Schedule;
import com.mg.challenge.pojos.SchedulePK;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, SchedulePK> {

	@Query("SELECT s FROM Schedule s WHERE s.primaryKey.driverSSN = :driverSSN")
	public List<Schedule> findScheduleByDriverSSN(@Param("driverSSN") String driverSSN);

	@Query("SELECT s FROM Schedule s WHERE s.primaryKey.busID = :busID")
	public List<Schedule> findScheduleByBusID(@Param("busID") Integer busID);
}
