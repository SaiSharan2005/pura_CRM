package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.LogisticPersonDetails;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.LogisticPersonDetailsDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.repository.LogisticPersonDetailsRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.LogisticPersonDetailsService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LogisticPersonDetailsServiceImpl implements LogisticPersonDetailsService {

    @Autowired
    private LogisticPersonDetailsRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LogisticPersonDetailsDTO createLogisticPersonDetails(Long userId, LogisticPersonDetailsDTO dto) {
        if (Objects.isNull(userId) || Objects.isNull(dto)) {
            throw new CustomSecurityException("Invalid input data", HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomSecurityException("User not found with ID: " + userId, HttpStatus.NOT_FOUND));

        dto.setUser(user);
        LogisticPersonDetails entity = modelMapper.map(dto, LogisticPersonDetails.class);
        entity = repository.save(entity);

        return modelMapper.map(entity, LogisticPersonDetailsDTO.class);
    }

    @Override
    public List<LogisticPersonDetailsDTO> getAllLogisticPersons() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, LogisticPersonDetailsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LogisticPersonDetailsDTO getLogisticPersonById(Long id) {
        LogisticPersonDetails entity = repository.findByUserId(id)
                .orElseThrow(() -> new CustomSecurityException("Logistic person not found with ID: " + id, HttpStatus.NOT_FOUND));
        return modelMapper.map(entity, LogisticPersonDetailsDTO.class);
    }

    @Override
    public LogisticPersonDetailsDTO updateLogisticPersonById(Long id, LogisticPersonDetailsDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("User not found with ID: " + id, HttpStatus.NOT_FOUND));

        dto.setUser(user);
        LogisticPersonDetails existingEntity = repository.findByUser(user);

        if (existingEntity == null) {
            throw new CustomSecurityException("Logistic person not found with ID: " + id, HttpStatus.NOT_FOUND);
        }

        modelMapper.map(dto, existingEntity);
        LogisticPersonDetails updatedEntity = repository.save(existingEntity);

        return modelMapper.map(updatedEntity, LogisticPersonDetailsDTO.class);
    }

    @Override
    public void deleteLogisticPersonById(Long id) {
        if (!repository.existsByUserId(id)) {
            throw new CustomSecurityException("Logistic person not found with ID: " + id, HttpStatus.NOT_FOUND);
        }

        repository.deleteByUserId(id);
    }
}
