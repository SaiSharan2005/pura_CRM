package com.crm.springbootjwtimplementation.repository;

import com.crm.springbootjwtimplementation.domain.LogisticPersonDetails;
import com.crm.springbootjwtimplementation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogisticPersonDetailsRepository extends JpaRepository<LogisticPersonDetails, Long> {

    LogisticPersonDetails findByUser(User user);

    Optional<LogisticPersonDetails> findByUserId(Long id);

    boolean existsByUserId(Long id);

    void deleteByUserId(Long id);
}
