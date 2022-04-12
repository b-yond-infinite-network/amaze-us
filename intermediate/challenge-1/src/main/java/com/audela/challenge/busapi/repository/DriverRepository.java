package com.audela.challenge.busapi.repository;

import com.audela.challenge.busapi.entity.DriverEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Repository
public interface DriverRepository extends CrudRepository<DriverEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update DriverEntity a set a.firstName = :firstName, a.lastName = :lastName, " +
            "a.ssn = :ssn, a.email = :email where a.id = :id")
    int updateDriver(@Param(value = "id") int id,
                       @Param(value = "firstName") String firstName,
                       @Param(value = "lastName") String lastName,
                       @Param(value = "ssn") String ssn,
                       @Param(value = "email") String email);
}
