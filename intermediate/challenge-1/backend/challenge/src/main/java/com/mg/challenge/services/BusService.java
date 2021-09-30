package com.mg.challenge.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mg.challenge.exceptions.BusIDException;
import com.mg.challenge.pojos.Bus;
import com.mg.challenge.pojos.Driver;
import com.mg.challenge.repositories.BusRepository;
import com.mg.challenge.repositories.DriverRepository;

@Service
public class BusService {

	@Autowired
	private BusRepository busRepository;

	@Autowired
	private DriverRepository driverRepository;

	public Iterable<Bus> findAllBuss() {
		return busRepository.findAll();
	}

	public Bus findBusByID(Integer id) {
		Optional<Bus> optionalRef = busRepository.findById(id);
		if (optionalRef.isPresent())
			return optionalRef.get();
		throw new BusIDException("Bus with ID: " + id + " not found");
	}

	public void deleteBusByID(Integer id) {
		Bus bus = id == null ? null : busRepository.getById(id);
		busRepository.delete(bus);
	}

	public Bus saveOrUpdateBus(@Valid Bus bus) {
		try {
			String ssn = bus.getDriverSSN();

			Optional<Driver> optionalRef = driverRepository.findById(ssn == null ? "" : ssn);
			if (optionalRef.isPresent()) {
				bus.setDriverSSN(optionalRef.get().getSsn());
				bus.setAssociatedDriver(optionalRef.get());
			} else {
				bus.setDriverSSN(null);
				bus.setAssociatedDriver(null);
			}
			return busRepository.save(bus);
		} catch (Exception e) {
			throw new BusIDException("Bus ID '" + bus.getId() + "' already exists");
		}
	}

}
