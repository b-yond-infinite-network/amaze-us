package com.mg.challenge.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mg.challenge.pojos.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

	public Optional<Schedule> findByBusIDAndDriverSSN(Integer busID, String driverSSN);
	
	@Query("SELECT s FROM Schedule s WHERE s.driverSSN = :driverSSN")
	public List<Schedule> findSchedulesByDriverSSN(@Param("driverSSN") String driverSSN);

	@Query("SELECT s FROM Schedule s WHERE s.driverSSN = :driverSSN and s.day between :from and :to")
	public Iterable<Schedule> findSchedulesByDriverSSN(@Param("driverSSN") String ssn, @Param("from") Date from, @Param("to") Date to);

	@Query("SELECT s FROM Schedule s WHERE s.busID = :busID")
	public List<Schedule> findSchedulesByBusID(@Param("busID") Integer busID);

	@Query("SELECT s FROM Schedule s WHERE s.busID = :busID and s.day between :from and :to")
	public Iterable<Schedule> findSchedulesByBusID(@Param("busID") Integer busID, @Param("from") Date from, @Param("to") Date to);

	@Query("SELECT s FROM Schedule s WHERE s.day between :from and :to")
	public Iterable<Schedule> findSchedulesBetweenDates(Date from, Date to);

}
