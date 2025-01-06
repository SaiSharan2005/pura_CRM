package com.crm.springbootjwtimplementation.repository;

import com.crm.springbootjwtimplementation.domain.DealDetails;
import com.crm.springbootjwtimplementation.domain.Delivery;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByLogisticPersonId(Long logisticPersonId);  // Corrected method signature

    List<Delivery> findByLogisticPersonUserId(Long id);

}
