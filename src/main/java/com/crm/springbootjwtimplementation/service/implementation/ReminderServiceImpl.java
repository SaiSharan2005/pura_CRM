package com.crm.springbootjwtimplementation.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.springbootjwtimplementation.domain.DealDetails;
import com.crm.springbootjwtimplementation.domain.Reminder;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.dto.ReminderRequestDTO;
import com.crm.springbootjwtimplementation.dto.ReminderResponseDTO;
import com.crm.springbootjwtimplementation.repository.DealDetailsRepository;
import com.crm.springbootjwtimplementation.repository.ReminderRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.ReminderService;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DealDetailsRepository dealDetailsRepository;

    @Override
    public ReminderResponseDTO createReminder(ReminderRequestDTO reminderRequestDTO, Long userId) {
        Reminder reminder = new Reminder();
        reminder.setTitle(reminderRequestDTO.getTitle());
        reminder.setNote(reminderRequestDTO.getNote());
        reminder.setLocation(reminderRequestDTO.getLocation());
        reminder.setReminderDate(reminderRequestDTO.getReminderDate());

        // Set the user who is creating the reminder.
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        reminder.setUser(user);

        // Optionally, set the associated deal.
        if(reminderRequestDTO.getDealId() != null) {
            DealDetails deal = dealDetailsRepository.findById(reminderRequestDTO.getDealId())
                .orElseThrow(() -> new RuntimeException("Deal not found"));
            reminder.setDeal(deal);
        }

        Reminder saved = reminderRepository.save(reminder);
        return mapToResponseDTO(saved);
    }

    @Override
    public List<ReminderResponseDTO> getAllReminders(Long userId) {
        List<Reminder> reminders = reminderRepository.findByUserId(userId);
        return reminders.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public ReminderResponseDTO getReminderById(Long id, Long userId) {
        Reminder reminder = reminderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reminder not found"));
        if (!reminder.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }
        return mapToResponseDTO(reminder);
    }

    @Override
    public ReminderResponseDTO updateReminder(Long id, ReminderRequestDTO reminderRequestDTO, Long userId) {
        Reminder reminder = reminderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reminder not found"));
        if (!reminder.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        if(reminderRequestDTO.getTitle() != null) {
            reminder.setTitle(reminderRequestDTO.getTitle());
        }
        if(reminderRequestDTO.getNote() != null) {
            reminder.setNote(reminderRequestDTO.getNote());
        }
        if(reminderRequestDTO.getLocation() != null) {
            reminder.setLocation(reminderRequestDTO.getLocation());
        }
        if(reminderRequestDTO.getReminderDate() != null) {
            reminder.setReminderDate(reminderRequestDTO.getReminderDate());
        }
        if(reminderRequestDTO.getDealId() != null) {
            DealDetails deal = dealDetailsRepository.findById(reminderRequestDTO.getDealId())
                .orElseThrow(() -> new RuntimeException("Deal not found"));
            reminder.setDeal(deal);
        }

        Reminder updated = reminderRepository.save(reminder);
        return mapToResponseDTO(updated);
    }

    @Override
    public List<ReminderResponseDTO> getActiveReminders(Long userId) {
        List<Reminder> reminders = reminderRepository.findByUserIdAndIsActiveTrue(userId);
        return reminders.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public ReminderResponseDTO updateReminderActiveStatus(Long id, boolean isActive, Long userId) {
        Reminder reminder = reminderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reminder not found"));
        if (!reminder.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }
        reminder.setActive(isActive);
        Reminder updated = reminderRepository.save(reminder);
        return mapToResponseDTO(updated);
    }

    // Mapping helper method
    private ReminderResponseDTO mapToResponseDTO(Reminder reminder) {
        ReminderResponseDTO dto = new ReminderResponseDTO();
        dto.setId(reminder.getId());
        dto.setTitle(reminder.getTitle());
        dto.setNote(reminder.getNote());
        dto.setLocation(reminder.getLocation());
        if(reminder.getDeal() != null) {
            // For endpoints that require only the deal id, return its id.
            dto.setDealId(reminder.getDeal().getId());
        }
        dto.setUserId(reminder.getUser().getId());
        dto.setActive(reminder.isActive());
        dto.setReminderDate(reminder.getReminderDate());
        dto.setCreatedAt(reminder.getCreatedAt());
        dto.setUpdatedAt(reminder.getUpdatedAt());
        return dto;
    }
}
