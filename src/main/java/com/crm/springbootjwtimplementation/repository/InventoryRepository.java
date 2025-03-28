package com.crm.springbootjwtimplementation.repository;

import com.crm.springbootjwtimplementation.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
