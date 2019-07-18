package com.kepler.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kepler.exception.ResourceException;
import com.kepler.model.BabyRequest;

public interface BabyRequestService {

	/* GENERAL METHODS
	 * *************** */
	Page<BabyRequest> getAllBabyRequests(Pageable pageable);
	
	/* GET METHOD 
	 * *********** */
	Page<BabyRequest> getByRequestId(Long requestId, Pageable pageable) throws ResourceException;
	
	Page<BabyRequest> getByRequestCreateDate(Date requestCreateDate, Pageable pageable) throws ResourceException;
	Page<BabyRequest> getByRequestSubmitDate(Date requestSubmitDate, Pageable pageable) throws ResourceException;
	Page<BabyRequest> getByRequestAcceptDate1(Date requestAcceptDate1, Pageable pageable) throws ResourceException;
	Page<BabyRequest> getByRequestAcceptDate2(Date requestAcceptDate2, Pageable pageable) throws ResourceException;
	Page<BabyRequest> getByRequestRefuseDate1(Date requestRefuseDate1, Pageable pageable) throws ResourceException;
	Page<BabyRequest> getByRequestRefuseDate2(Date requestRefuseDate2, Pageable pageable) throws ResourceException;
	Page<BabyRequest> getByRequestScheduleDate(Date requestScheduleDate, Pageable pageable) throws ResourceException;
	Page<BabyRequest> getByRequestCloseDate(Date requestCloseDate, Pageable pageable) throws ResourceException;

	Page<BabyRequest> getByBabyLogin(String babyLogin, Pageable pageable) throws ResourceException;
	Page<BabyRequest> getByBabyEmail(String babyEmail, Pageable pageable) throws ResourceException;
	Page<BabyRequest> getByBabyFirstName(String babyFirstName, Pageable pageable) throws ResourceException;
	Page<BabyRequest> getByBabyLastName(String babyLastName, Pageable pageable) throws ResourceException;
	Page<BabyRequest> getByBabySex(boolean babySex, Pageable pageable) throws ResourceException;
		
	BabyRequest saveOrUpdateBabyRequest(BabyRequest babyRequest, Pageable pageable) throws ResourceException;
	BabyRequest deleteRequestId(Long requestId, Pageable pageable) throws ResourceException;
}