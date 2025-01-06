package com.crm.springbootjwtimplementation.repository;

import com.crm.springbootjwtimplementation.domain.ManagerDetails;
import com.crm.springbootjwtimplementation.domain.User;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerDetailsRepository extends JpaRepository<ManagerDetails, Long> {

    ManagerDetails findByUser(User user);

    Optional<ManagerDetails> findByUserId(Long id);

    boolean existsByUserId(Long id);

    @Modifying
    @Transactional
    void deleteByUserId(Long id);
}
