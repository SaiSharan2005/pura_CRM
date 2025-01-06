package com.crm.springbootjwtimplementation.repository;

import com.crm.springbootjwtimplementation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    Optional<User>  findById(Long id);


    boolean existsByUsername(String username);
}
