package com.ms.reminder.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ms.reminder.model.Reminder;

public interface AppRepository extends JpaRepository<Reminder, Long> {
	
	@Query("SELECT rem FROM Reminder rem WHERE date between :startTime AND :endTime")
	public List<Reminder> getRemindersWithinTimeRange(@Param("startTime")LocalDateTime fromTime,@Param("endTime")LocalDateTime toTime);

}
