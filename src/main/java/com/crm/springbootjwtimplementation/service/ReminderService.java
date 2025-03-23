package com.crm.springbootjwtimplementation.service;

import java.util.List;

import com.crm.springbootjwtimplementation.domain.dto.ReminderRequestDTO;
import com.crm.springbootjwtimplementation.domain.dto.ReminderResponseDTO;

public interface ReminderService {
    ReminderResponseDTO createReminder(ReminderRequestDTO reminderRequestDTO, Long userId);
    List<ReminderResponseDTO> getAllReminders(Long userId);
    ReminderResponseDTO getReminderById(Long id, Long userId);
    ReminderResponseDTO updateReminder(Long id, ReminderRequestDTO reminderRequestDTO, Long userId);
    List<ReminderResponseDTO> getActiveReminders(Long userId);
    ReminderResponseDTO updateReminderActiveStatus(Long id, boolean isActive, Long userId);
}
