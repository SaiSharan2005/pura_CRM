package com.crm.springbootjwtimplementation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.springbootjwtimplementation.dto.ReminderActiveRequestDTO;
import com.crm.springbootjwtimplementation.dto.ReminderRequestDTO;
import com.crm.springbootjwtimplementation.dto.ReminderResponseDTO;
import com.crm.springbootjwtimplementation.dto.ResponseMessageDTO;
import com.crm.springbootjwtimplementation.dto.users.TokenResponseDTO;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.service.ReminderService;

@RestController
@RequestMapping("/api/remainder")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @Autowired
    private AuthService authService;

    // 1. POST /api/remainder/create
    @PostMapping("/create")
    public ResponseMessageDTO createReminder(@RequestBody ReminderRequestDTO reminderRequestDTO) {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        Long userId = userToken.getId();
        reminderService.createReminder(reminderRequestDTO, userId);
        ResponseMessageDTO response = new ResponseMessageDTO();
        response.setMessage("Reminder created successfully");
        response.setSuccess(true);
        return response;
    }

    // 2. GET /api/remainder/all
    @GetMapping("/all")
    public List<ReminderResponseDTO> getAllReminders() {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        Long userId = userToken.getId();
        return reminderService.getAllReminders(userId);
    }

    // 3. GET /api/remainder/{id}
    @GetMapping("/{id}")
    public ReminderResponseDTO getReminderById(@PathVariable Long id) {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        Long userId = userToken.getId();
        return reminderService.getReminderById(id, userId);
    }

    // 4. PUT /api/remainder/update/{id}
    @PutMapping("/update/{id}")
    public ResponseMessageDTO updateReminder(@PathVariable Long id, @RequestBody ReminderRequestDTO reminderRequestDTO) {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        Long userId = userToken.getId();
        reminderService.updateReminder(id, reminderRequestDTO, userId);
        ResponseMessageDTO response = new ResponseMessageDTO();
        response.setMessage("Reminder updated successfully");
        response.setSuccess(true);
        return response;
    }

    // 5. GET /api/remainder/active
    @GetMapping("/active")
    public List<ReminderResponseDTO> getActiveReminders() {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        Long userId = userToken.getId();
        return reminderService.getActiveReminders(userId);
    }

    // 6. PATCH /api/remainder/active/{id} – update active status
    @PatchMapping("/active/{id}")
    public ResponseMessageDTO updateActiveStatus(@PathVariable Long id, @RequestBody ReminderActiveRequestDTO activeRequest) {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        Long userId = userToken.getId();
        System.out.println(activeRequest.isActive());
        System.out.println("getljlkj");
        reminderService.updateReminderActiveStatus(id, activeRequest.isActive(), userId);
        ResponseMessageDTO response = new ResponseMessageDTO();
        response.setMessage("Reminder active status updated successfully");
        response.setSuccess(true);
        return response;
    }
}
