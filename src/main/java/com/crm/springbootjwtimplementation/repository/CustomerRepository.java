// Customer Repository
package com.crm.springbootjwtimplementation.repository;

import com.crm.springbootjwtimplementation.domain.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findById(Long id);
    boolean existsByEmail(String email);
}