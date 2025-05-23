package com.crm.springbootjwtimplementation.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.crm.springbootjwtimplementation.domain.SalesmanDetails;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.users.*;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.mapper.SalesmanDetailsMapper;
import com.crm.springbootjwtimplementation.repository.SalesmanDetailsRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.SalesmanDetailsService;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;

@Service
@RequiredArgsConstructor
public class SalesmanDetailsServiceImpl implements SalesmanDetailsService {

    private final SalesmanDetailsRepository repo;
    private final UserRepository userRepo;
    private final SalesmanDetailsMapper mapper;

    @Override
    @Transactional
    public SalesmanDetailsResponseDTO createSalesmanDetails(Long userId, SalesmanDetailsDTO dto) {
        if (userId == null || dto == null) {
            throw new CustomSecurityException(
                    ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new CustomSecurityException(
                        ApiMessages.USER_NOT_FOUND + userId, HttpStatus.NOT_FOUND));
        if (repo.findByUserId(userId).isPresent()) {
            throw new CustomSecurityException(
                    ApiMessages.SALESMAN_DETAILS_ALREADY_EXIST + userId,
                    HttpStatus.BAD_REQUEST);
        }
        SalesmanDetails entity = mapper.toEntity(dto);
        entity.setUser(user);

        SalesmanDetails saved = repo.save(entity);
        return mapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesmanDetailsResponseDTO> getAllSalesmanDetails(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new CustomSecurityException(
                    ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<SalesmanDetails> pagedEntities = repo.findAll(pageable);

        List<SalesmanDetailsResponseDTO> dtos = pagedEntities.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, pagedEntities.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public SalesmanDetailsResponseDTO getSalesmanDetailsByUserId(Long userId) {
        if (userId == null) {
            throw new CustomSecurityException(
                    ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        SalesmanDetails entity = repo.findByUserId(userId)
                .orElseThrow(() -> new CustomSecurityException(
                        ApiMessages.SALESMAN_NOT_FOUND + userId, HttpStatus.NOT_FOUND));

        return mapper.toResponseDto(entity);
    }

    @Override
    @Transactional
    public SalesmanDetailsResponseDTO updateSalesmanDetailsByUserId(
            Long userId, SalesmanDetailsDTO dto) {

        if (userId == null || dto == null) {
            throw new CustomSecurityException(
                    ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        SalesmanDetails existing = repo.findByUserId(userId)
                .orElseThrow(() -> new CustomSecurityException(
                        ApiMessages.SALESMAN_NOT_FOUND + userId, HttpStatus.NOT_FOUND));

        mapper.updateFromDto(dto, existing);
        SalesmanDetails updated = repo.save(existing);
        return mapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteSalesmanDetailsByUserId(Long userId) {
        if (userId == null) {
            throw new CustomSecurityException(
                    ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        SalesmanDetails existing = repo.findByUserId(userId)
                .orElseThrow(() -> new CustomSecurityException(
                        ApiMessages.SALESMAN_NOT_FOUND + userId, HttpStatus.NOT_FOUND));

        repo.delete(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public SalesmanDetailsResponseDTO getSalesmanDetailsByUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new CustomSecurityException(
                    ApiMessages.INVALID_USERNAME, HttpStatus.BAD_REQUEST);
        }

        SalesmanDetails entity = repo.findByUserUsername(username)
                .orElseThrow(() -> new CustomSecurityException(
                        ApiMessages.SALESMAN_NOT_FOUND + username, HttpStatus.NOT_FOUND));

        return mapper.toResponseDto(entity);
    }

    @Override
    @Transactional
    public SalesmanDetailsResponseDTO updateSalesmanDetailsByUsername(
            String username, SalesmanDetailsDTO dto) {

        if (username == null || username.isBlank() || dto == null) {
            throw new CustomSecurityException(
                    ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        SalesmanDetails existing = repo.findByUserUsername(username)
                .orElseThrow(() -> new CustomSecurityException(
                        ApiMessages.SALESMAN_NOT_FOUND + username, HttpStatus.NOT_FOUND));

        mapper.updateFromDto(dto, existing);
        SalesmanDetails updated = repo.save(existing);
        return mapper.toResponseDto(updated);
    }
}
