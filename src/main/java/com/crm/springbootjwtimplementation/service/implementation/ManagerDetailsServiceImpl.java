package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.ManagerDetails;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.ManagerDetailsDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.repository.ManagerDetailsRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.ManagerDetailsService;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ManagerDetailsServiceImpl implements ManagerDetailsService {

    @Autowired
    private ManagerDetailsRepository managerDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ManagerDetailsDTO createManagerDetails(Long userId, ManagerDetailsDTO managerDetailsDTO) {
        if (Objects.isNull(userId) || Objects.isNull(managerDetailsDTO)) {
            throw new CustomSecurityException(ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomSecurityException(ApiMessages.USER_NOT_FOUND + userId, HttpStatus.NOT_FOUND));

        managerDetailsDTO.setUser(user);
        ManagerDetails managerDetails = modelMapper.map(managerDetailsDTO, ManagerDetails.class);
        managerDetails = managerDetailsRepository.save(managerDetails);

        return modelMapper.map(managerDetails, ManagerDetailsDTO.class);
    }

    @Override
    public List<ManagerDetailsDTO> getAllManagerDetails() {
        return managerDetailsRepository.findAll().stream()
                .map(manager -> modelMapper.map(manager, ManagerDetailsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ManagerDetailsDTO getManagerDetailsById(Long id) {
        if (Objects.isNull(id)) {
            throw new CustomSecurityException(ApiMessages.INVALID_ID, HttpStatus.BAD_REQUEST);
        }

        ManagerDetails managerDetails = managerDetailsRepository.findByUserId(id)
                .orElseThrow(() -> new CustomSecurityException(ApiMessages.MANAGER_NOT_FOUND + id, HttpStatus.NOT_FOUND));
        return modelMapper.map(managerDetails, ManagerDetailsDTO.class);
    }

    @Override
    public ManagerDetailsDTO updateManagerDetailsById(Long id, ManagerDetailsDTO managerDetailsDTO) {
        if (Objects.isNull(id) || Objects.isNull(managerDetailsDTO)) {
            throw new CustomSecurityException(ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException(ApiMessages.USER_NOT_FOUND + id, HttpStatus.NOT_FOUND));

        managerDetailsDTO.setUser(user);
        ManagerDetails existingManager = managerDetailsRepository.findByUser(user);

        if (existingManager == null) {
            throw new CustomSecurityException(ApiMessages.MANAGER_NOT_FOUND + id, HttpStatus.NOT_FOUND);
        }

        modelMapper.map(managerDetailsDTO, existingManager);
        ManagerDetails updatedManager = managerDetailsRepository.save(existingManager);

        return modelMapper.map(updatedManager, ManagerDetailsDTO.class);
    }

    @Override
    public void deleteManagerDetailsById(Long id) {
        if (Objects.isNull(id)) {
            throw new CustomSecurityException(ApiMessages.INVALID_ID, HttpStatus.BAD_REQUEST);
        }

        if (!managerDetailsRepository.existsByUserId(id)) {
            throw new CustomSecurityException(ApiMessages.MANAGER_NOT_FOUND + id, HttpStatus.NOT_FOUND);
        }

        managerDetailsRepository.deleteByUserId(id);
    }
}
