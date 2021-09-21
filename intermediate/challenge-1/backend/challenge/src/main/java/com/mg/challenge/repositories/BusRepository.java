package com.mg.challenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mg.challenge.pojos.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {

}
