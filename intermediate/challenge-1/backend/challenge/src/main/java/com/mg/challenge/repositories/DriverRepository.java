package com.mg.challenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mg.challenge.pojos.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {

	/* Write here custom queries */
//    public Driver findDriverBySsn(String ssn);
}
