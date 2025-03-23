package com.crm.springbootjwtimplementation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.springbootjwtimplementation.domain.Target;

public interface TargetRepository extends JpaRepository<Target, Long> {
    // Return all targets for a given monthYear
    List<Target> findByMonthYear(String monthYear);
    
    // Return all targets for a given salesman (using salesman id)
    List<Target> findBySalesmanId(Long salesmanId);
}
