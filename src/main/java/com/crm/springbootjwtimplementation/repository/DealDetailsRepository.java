package com.crm.springbootjwtimplementation.repository;

import com.crm.springbootjwtimplementation.domain.Customer;
import com.crm.springbootjwtimplementation.domain.DealDetails;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealDetailsRepository extends JpaRepository<DealDetails, Long> {

    List<DealDetails> findByUserIdId(Long id);
}