package com.mg.challenge.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mg.challenge.exceptions.DriverSSNException;
import com.mg.challenge.pojos.Driver;
import com.mg.challenge.repositories.DriverRepository;

@Service
public class DriverService {

	@Autowired
	private DriverRepository driverRepository;

	public Iterable<Driver> findAllDrivers() {
		return driverRepository.findAll();
	}

	public Driver findDriverBySSN(String ssn) {
		String modifiedSSN = ssn != null ? ssn.trim().toUpperCase() : "";
		Optional<Driver> optionalRef = driverRepository.findById(modifiedSSN);
		if (optionalRef.isPresent())
			return optionalRef.get();

		throw new DriverSSNException("Driver with SSN: " + modifiedSSN + " not found");
	}

	public void deleteDriverBySSN(String ssn) {
		Driver driver = findDriverBySSN(ssn);
		driverRepository.delete(driver);
	}

	public Driver saveOrUpdateDriver(@Valid Driver driver) {
		try {
			driver.setSsn(driver.getSsn().toUpperCase());
			return driverRepository.save(driver);
		} catch (Exception e) {
			throw new DriverSSNException("Driver SSN '" + driver.getSsn().toUpperCase() + "' already exists");
		}
	}

}
