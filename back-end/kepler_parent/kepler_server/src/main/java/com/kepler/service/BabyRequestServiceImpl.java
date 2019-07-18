package com.kepler.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kepler.exception.ResourceException;
import com.kepler.model.BabyRequest;
import com.kepler.repository.BabyRequestRepository;

@Service(value = "BabyRequestService")
public class BabyRequestServiceImpl implements BabyRequestService {

	public static final DateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.FRENCH);
	
	public static DateFormat getDateFormatter() {
		return date_formatter;
	}

	@Autowired
	private BabyRequestRepository BabyRequestRepository;
	
	@Override
	public Page<BabyRequest> getAllBabyRequests(Pageable pageable) {
		return BabyRequestRepository.findAll(pageable);
	}

	@Override
	public Page<BabyRequest> getByRequestId(Long requestId, Pageable pageable)
			throws ResourceException {
		
		if (!(requestId instanceof Long && requestId >= 0)) {
			throw new ResourceException("[BabyRequestServiceImpl.java] getByRequestId", "Can't get the BABY_REQUEST with requestId=" + requestId + 
					" because requestId passed isn't a positive integer."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return BabyRequestRepository.findByRequestId(requestId, pageable);
	}

	@Override
	public Page<BabyRequest> getByRequestCreateDate(Date requestCreateDate, Pageable pageable)
			throws ResourceException {
		
		if (!(requestCreateDate instanceof Date)) {
			throw new ResourceException("[BabyRequestServiceImpl.java] getByRequestCreateDate", "Can't get all the BABY_REQUEST with requestCreateDate=" + requestCreateDate + 
					" because requestCreateDate passed isn't a Date."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return BabyRequestRepository.findByRequestCreateDate(requestCreateDate, pageable);
	}
	
	@Override
	public Page<BabyRequest> getByRequestSubmitDate(Date requestSubmitDate, Pageable pageable)
			throws ResourceException {
		
		if (!(requestSubmitDate instanceof Date)) {
			throw new ResourceException("[BabyRequestServiceImpl.java] getByRequestSubmitDate", "Can't get all the BABY_REQUEST with requestSubmitDate=" + requestSubmitDate + 
					" because requestSubmitDate passed isn't a Date."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return BabyRequestRepository.findByRequestSubmitDate(requestSubmitDate, pageable);
	}
	
	@Override
	public Page<BabyRequest> getByRequestAcceptDate1(Date requestAcceptDate1, Pageable pageable)
			throws ResourceException {
		
		if (!(requestAcceptDate1 instanceof Date)) {
			throw new ResourceException("[BabyRequestServiceImpl.java] getByRequestAcceptDate1", "Can't get all the BABY_REQUEST with requestAcceptDate1=" + requestAcceptDate1 + 
					" because requestAcceptDate1 passed isn't a Date."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return BabyRequestRepository.findByRequestAcceptDate1(requestAcceptDate1, pageable);
	}
	
	@Override
	public Page<BabyRequest> getByRequestAcceptDate2(Date requestAcceptDate2, Pageable pageable)
			throws ResourceException {
		
		if (!(requestAcceptDate2 instanceof Date)) {
			throw new ResourceException("[BabyRequestServiceImpl.java] getByRequestAcceptDate2", "Can't get all the BABY_REQUEST with requestAcceptDate2=" + requestAcceptDate2 + 
					" because requestAcceptDate2 passed isn't a Date."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return BabyRequestRepository.findByRequestAcceptDate2(requestAcceptDate2, pageable);
	}
	
	@Override
	public Page<BabyRequest> getByRequestRefuseDate1(Date requestRefuseDate1, Pageable pageable)
			throws ResourceException {
		
		if (!(requestRefuseDate1 instanceof Date)) {
			throw new ResourceException("[BabyRequestServiceImpl.java] getByRequestRefuseDate1", "Can't get all the BABY_REQUEST with requestRefuseDate1=" + requestRefuseDate1 + 
					" because requestRefuseDate1 passed isn't a Date."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return BabyRequestRepository.findByRequestRefuseDate1(requestRefuseDate1, pageable);
	}
	
	@Override
	public Page<BabyRequest> getByRequestRefuseDate2(Date requestRefuseDate2, Pageable pageable)
			throws ResourceException {
		
		if (!(requestRefuseDate2 instanceof Date)) {
			throw new ResourceException("[BabyRequestServiceImpl.java] getByRequestRefuseDate2", "Can't get all the BABY_REQUEST with requestRefuseDate2=" + requestRefuseDate2 + 
					" because requestRefuseDate2 passed isn't a Date."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return BabyRequestRepository.findByRequestRefuseDate2(requestRefuseDate2, pageable);
	}
	
	@Override
	public Page<BabyRequest> getByRequestScheduleDate(Date requestScheduleDate, Pageable pageable)
			throws ResourceException {
		
		if (!(requestScheduleDate instanceof Date)) {
			throw new ResourceException("[BabyRequestServiceImpl.java] getByRequestScheduleDate", "Can't get all the BABY_REQUEST with requestScheduleDate=" + requestScheduleDate + 
					" because requestScheduleDate passed isn't a Date."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return BabyRequestRepository.findByRequestScheduleDate(requestScheduleDate, pageable);
	}
	
	@Override
	public Page<BabyRequest> getByRequestCloseDate(Date requestCloseDate, Pageable pageable)
			throws ResourceException {
		
		if (!(requestCloseDate instanceof Date)) {
			throw new ResourceException("[BabyRequestServiceImpl.java] getByRequestCloseDate", "Can't get all the BABY_REQUEST with requestCloseDate=" + requestCloseDate + 
					" because requestCloseDate passed isn't a Date."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return BabyRequestRepository.findByRequestCloseDate(requestCloseDate, pageable);
	}

	@Override
	public Page<BabyRequest> getByBabyLogin(String babyLogin, Pageable pageable) throws ResourceException {
				
		if (!(babyLogin instanceof String && babyLogin != "")) {
			throw new ResourceException("[BabyRequestServiceImpl.java] getByBabyLogin", "Can't get all the BABY_REQUEST with babyLogin=" + babyLogin + 
					" because babyLogin passed isn't a none empty String."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return BabyRequestRepository.findByBabyLogin(babyLogin, pageable);
	}
	
	@Override
	public Page<BabyRequest> getByBabyEmail(String babyEmail, Pageable pageable) throws ResourceException {
				
		if (!(babyEmail instanceof String && babyEmail != "")) {
			throw new ResourceException("[BabyRequestServiceImpl.java] getByBabyEmail", "Can't get all the BABY_REQUEST with babyEmail=" + babyEmail + 
					" because babyEmail passed isn't a none empty String."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return BabyRequestRepository.findByBabyEmail(babyEmail, pageable);
	}
	
	@Override
	public Page<BabyRequest> getByBabyFirstName(String babyFirstName, Pageable pageable) throws ResourceException {
				
		if (!(babyFirstName instanceof String &&babyFirstName != "")) {
			throw new ResourceException("[BabyRequestServiceImpl.java] getByBabyFirstName", "Can't get all the BABY_REQUEST with babyFirstName=" + babyFirstName + 
					" because babyFirstName passed isn't a none empty String."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return BabyRequestRepository.findByBabyFirstName(babyFirstName, pageable);
	}
	
	@Override
	public Page<BabyRequest> getByBabyLastName(String babyLastName, Pageable pageable) throws ResourceException {
				
		if (!(babyLastName instanceof String && babyLastName != "")) {
			throw new ResourceException("[BabyRequestServiceImpl.java] getByBabyLastName", "Can't get all the BABY_REQUEST with babyLastName=" + babyLastName + 
					" because babyLastName passed isn't a none empty String."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return BabyRequestRepository.findByBabyLastName(babyLastName, pageable);
	}
	
	@Override
	public Page<BabyRequest> getByBabySex(boolean babySex, Pageable pageable) throws ResourceException {

		return BabyRequestRepository.findByBabySex(babySex, pageable);
	}

	@Override
	public BabyRequest saveOrUpdateBabyRequest(BabyRequest babyRequest, Pageable pageable)
			throws ResourceException {
		
		babyRequest.init();
		
		if (babyRequest.check_creation_constraint() != true) {
			throw new ResourceException("[BabyRequestServiceImpl.java] saveOrUpdateBabyRequest", "Can't create or update the baby_request with the requestCreateDate=" + babyRequest.getRequestCreateDate() + 
					" because requestCreateDate doesn't respect the Dates constraints"
					, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (babyRequest.check_submit_constraint() != true) {
			throw new ResourceException("[BabyRequestServiceImpl.java] saveOrUpdateBabyRequest", "Can't create or update the baby_request with the requestSubmitDate=" + babyRequest.getRequestSubmitDate() + 
					" because requestSubmitDate doesn't respect the Dates constraints"
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		/* other constraints checkers not implemented for timer reasons however :
		 *  - setting the submit date with not null value shall also check if login, password and email are all set
		 *  - setting the close date with not null value shall also check if sex is set and the creation of the baby as pioneer shall be done*/

		// save the resource
		try {			

			return BabyRequestRepository.save(babyRequest);
			
		} 
		catch (Exception ex) {
			throw new ResourceException("[BabyRequestServiceImpl.java] saveOrUpdateBabyRequest", "Can't create or update the baby_request=" + babyRequest + 
					" because the save method raised an error and failed."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public BabyRequest deleteRequestId(Long requestId, Pageable pageable) throws ResourceException {
		
		Page<BabyRequest> babyRequest_found = null;

		if (requestId < 0) {
			throw new ResourceException("[BabyRequestServiceImpl.java] deleteRequestId", "Can't delete the baby_request with the requestId=" + requestId + 
					" because requestId isn't an positive integer."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		/* Check if another element share the same element id */
		babyRequest_found = BabyRequestRepository.findByRequestId(requestId, pageable);
		if (babyRequest_found.getNumberOfElements() != 1) {
			throw new ResourceException("[BabyRequestServiceImpl.java] deleteRequestId", "Can't delete the baby_request with the requestId=" + requestId + 
					" because " + babyRequest_found.getNumberOfElements() + " baby_request(s) found in database."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {			

			BabyRequestRepository.deleteById(requestId);
			
		} 
		catch (Exception ex) {
			throw new ResourceException("[BabyRequestServiceImpl.java] deleteRequestId", "Can't delete the requestId=" + requestId + 
					" because the deleteById method raised an error and failed."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		/* return the first and unique resource of the list */
		return babyRequest_found.getContent().get(0);
	}
	
}