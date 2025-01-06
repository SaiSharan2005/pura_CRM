package com.crm.springbootjwtimplementation.repository;

import com.crm.springbootjwtimplementation.domain.SalesmanDetails;
import com.crm.springbootjwtimplementation.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesmanDetailsRepository extends JpaRepository<SalesmanDetails, Long> {
    Optional<SalesmanDetails> findByUserUsername(String username);
    SalesmanDetails findByUserId(Long id);
    SalesmanDetails findByUser(User user);
}
