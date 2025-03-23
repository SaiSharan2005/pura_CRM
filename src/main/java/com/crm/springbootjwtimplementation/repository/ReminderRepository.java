package com.crm.springbootjwtimplementation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.springbootjwtimplementation.domain.Reminder;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    // All reminders for a given user
    List<Reminder> findByUserId(Long userId);

    // All active reminders for a given user
    List<Reminder> findByUserIdAndIsActiveTrue(Long userId);

}
