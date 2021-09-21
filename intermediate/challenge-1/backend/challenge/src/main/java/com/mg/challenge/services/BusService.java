package com.mg.challenge.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mg.challenge.exceptions.BusIDException;
import com.mg.challenge.pojos.Bus;
import com.mg.challenge.repositories.BusRepository;

@Service
public class BusService {

	@Autowired
	private BusRepository busRepository;

	public Iterable<Bus> findAllBuss() {
		return busRepository.findAll();
	}

	public Bus findBusByID(Integer id) {
		Bus bus = id == null ? null : busRepository.getById(id);
		if (bus == null) 
			throw new BusIDException("Bus with SSN: " + id + " not found");
		return bus;
	}
	
	public void deleteBusByID(Integer id) {
		Bus bus = id == null ? null : busRepository.getById(id);
		busRepository.delete(bus);
    }

	public Bus saveOrUpdateBus(@Valid Bus bus) {
		try{
			bus.setId(bus.getId());
            return busRepository.save(bus);
        } catch (Exception e){
            throw new BusIDException("Bus SSN '" + bus.getId() + "' already exists");
        }
	}

}
