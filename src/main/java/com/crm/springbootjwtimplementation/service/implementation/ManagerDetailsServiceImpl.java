package com.crm.springbootjwtimplementation.service.implementation;

import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.crm.springbootjwtimplementation.domain.ManagerDetails;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.users.ManagerDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.ManagerDetailsResponseDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.mapper.ManagerDetailsMapper;
import com.crm.springbootjwtimplementation.repository.ManagerDetailsRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.ManagerDetailsService;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;

@Service
@RequiredArgsConstructor
public class ManagerDetailsServiceImpl implements ManagerDetailsService {

    private final ManagerDetailsRepository repo;
    private final UserRepository userRepo;
    private final ManagerDetailsMapper mapper;

    @Override
    @Transactional
    public ManagerDetailsDTO createManagerDetails(ManagerDetailsDTO dto) {
        // Validate input
        if (dto.getUserId() == null) {
            throw new CustomSecurityException(
                ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        // Load user
        User user = userRepo.findById(dto.getUserId())
            .orElseThrow(() -> new CustomSecurityException(
                ApiMessages.USER_NOT_FOUND + dto.getUserId(), HttpStatus.NOT_FOUND));

        // Prevent duplicates
        if (repo.existsByUserId(user.getId())) {
            throw new CustomSecurityException(
                "Manager details already exist for user " + user.getId(),
                HttpStatus.BAD_REQUEST);
        }

        // Map & save
        ManagerDetails entity = mapper.toEntity(dto);
        entity.setUser(user);
        ManagerDetails saved = repo.save(entity);

        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ManagerDetailsResponseDTO> getAllManagerDetails(int page, int size) {
        if (page < 0 || size < 1) {
            throw new CustomSecurityException(
                ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        Pageable pg = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<ManagerDetails> paged = repo.findAll(pg);

        return paged.map(mapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ManagerDetailsResponseDTO getManagerDetailsByUserId(Long userId) {
        if (userId == null) {
            throw new CustomSecurityException(
                ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        ManagerDetails entity = repo.findByUserId(userId)
            .orElseThrow(() -> new CustomSecurityException(
                ApiMessages.MANAGER_NOT_FOUND + userId, HttpStatus.NOT_FOUND));

        return mapper.toResponseDto(entity);
    }

    @Override
    @Transactional
    public ManagerDetailsDTO updateManagerDetailsByUserId(
            Long userId, ManagerDetailsDTO dto) {

        if (userId == null) {
            throw new CustomSecurityException(
                ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        ManagerDetails existing = repo.findByUserId(userId)
            .orElseThrow(() -> new CustomSecurityException(
                ApiMessages.MANAGER_NOT_FOUND + userId, HttpStatus.NOT_FOUND));

        mapper.updateFromDto(dto, existing);
        ManagerDetails updated = repo.save(existing);

        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteManagerDetailsByUserId(Long userId) {
        if (userId == null) {
            throw new CustomSecurityException(
                ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        if (!repo.existsByUserId(userId)) {
            throw new CustomSecurityException(
                ApiMessages.MANAGER_NOT_FOUND + userId, HttpStatus.NOT_FOUND);
        }

        repo.deleteByUserId(userId);
    }
}
