package com.crm.springbootjwtimplementation.repository;

import com.crm.springbootjwtimplementation.domain.DealDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DealDetailsRepository extends JpaRepository<DealDetails, Long> {
    List<DealDetails> findByUser_Id(Long id);
}
