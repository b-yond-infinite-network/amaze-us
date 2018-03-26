package com.amertkara.am.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.amertkara.am.model.entity.Account;

@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {
}
