package com.mg.challenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mg.challenge.pojos.Schedule;
import com.mg.challenge.pojos.SchedulePK;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, SchedulePK> {

}
