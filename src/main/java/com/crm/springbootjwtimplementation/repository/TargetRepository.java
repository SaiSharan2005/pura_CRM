package com.crm.springbootjwtimplementation.repository;

import com.crm.springbootjwtimplementation.domain.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {
    List<Target> findByAssignedToId(Long assignedToId); // Find targets by assignee
    List<Target> findByAssignedById(Long assignedById); // Find targets by assigner
    List<Target> findByAssignedToIdId(Long assignedToId);
}
