package com.kepler.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kepler.model.BabyRequest;

@Repository
public interface BabyRequestRepository extends JpaRepository<BabyRequest, Long> {
	
	/* ********************************************************************************************************* *
	 * CRUD requests are missing but are implemented due to the extension with JpaRepostroy<ENtity, primary_key> * 
	 * CRUD : Create / Read / Update and Delete requests                                                         * 
	 * ********************************************************************************************************* *
	 * If a function requires to apply a complex request, the association @Query(the_request) can be used before */
	
	/* Next queries are added due to custom needs */
	Page<BabyRequest> findByRequestId(Long requestId, Pageable pageable);
	
	Page<BabyRequest> findByRequestCreateDate(Date requestCreateDate, Pageable pageable);
	Page<BabyRequest> findByRequestSubmitDate(Date requestSubmitDate, Pageable pageable);
	Page<BabyRequest> findByRequestAcceptDate1(Date requestAcceptDate1, Pageable pageable);
	Page<BabyRequest> findByRequestAcceptDate2(Date requestAcceptDate2, Pageable pageable);
	Page<BabyRequest> findByRequestRefuseDate1(Date requestRefuseDate1, Pageable pageable);
	Page<BabyRequest> findByRequestRefuseDate2(Date requestRefuseDate2, Pageable pageable);
	Page<BabyRequest> findByRequestScheduleDate(Date requestScheduleDate, Pageable pageable);
	Page<BabyRequest> findByRequestCloseDate(Date requestCloseDate, Pageable pageable);
	
	Page<BabyRequest> findByBabyLogin(String babyLogin, Pageable pageable);
	Page<BabyRequest> findByBabyEmail(String babyEmail, Pageable pageable);
	Page<BabyRequest> findByBabyFirstName(String babyFirstName, Pageable pageable);
	Page<BabyRequest> findByBabyLastName(String babyLastName, Pageable pageable);
	Page<BabyRequest> findByBabySex(boolean babySex, Pageable pageable);
}
