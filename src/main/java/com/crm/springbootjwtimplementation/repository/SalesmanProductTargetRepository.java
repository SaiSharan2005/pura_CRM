package com.crm.springbootjwtimplementation.repository;

import com.crm.springbootjwtimplementation.domain.SalesmanProductTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SalesmanProductTargetRepository extends JpaRepository<SalesmanProductTarget, Long> {
    // Find all targets for a given month-year.
    List<SalesmanProductTarget> findByMonthYear(String monthYear);
    
    // Find all targets for a given salesman (using his id).
    List<SalesmanProductTarget> findBySalesmanId(Long salesmanId);
}
