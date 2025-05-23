package com.crm.springbootjwtimplementation.service.implementation;

import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.users.LogisticPersonDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.LogisticPersonDetailsResponseDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.mapper.LogisticPersonDetailsMapper;
import com.crm.springbootjwtimplementation.repository.LogisticPersonDetailsRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.LogisticPersonDetailsService;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;

@Service
@RequiredArgsConstructor
public class LogisticPersonDetailsServiceImpl
    implements LogisticPersonDetailsService {

  private final LogisticPersonDetailsRepository repo;
  private final UserRepository userRepo;
  private final LogisticPersonDetailsMapper mapper;

  @Override
  @Transactional
  public LogisticPersonDetailsDTO create(
        Long userId,
        LogisticPersonDetailsDTO dto) {

    if (dto == null) {
      throw new CustomSecurityException(
        ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
    }

    // ensure the User exists
    User user = userRepo.findById(userId)
      .orElseThrow(() -> new CustomSecurityException(
        ApiMessages.USER_NOT_FOUND + userId,
        HttpStatus.NOT_FOUND));

    // prevent duplicate details per user
    if (repo.existsByUserId(userId)) {
      throw new CustomSecurityException(
        ApiMessages.LOGISTIC_PERSON_EXISTS + userId,
        HttpStatus.CONFLICT);
    }

    // map DTO → Entity + attach user
    var entity = mapper.toEntity(dto);
    entity.setUser(user);

    var saved = repo.save(entity);
    return mapper.toDto(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<LogisticPersonDetailsResponseDTO> list(int page, int size) {
    if (page < 0 || size <= 0) {
      throw new CustomSecurityException(
        ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
    }

    Pageable pg = PageRequest.of(page, size, Sort.by("id").ascending());
    return repo.findAll(pg)
               .map(mapper::toResponseDto);
  }

  @Override
  @Transactional(readOnly = true)
  public LogisticPersonDetailsResponseDTO getByUserId(Long userId) {
    var entity = repo.findByUserId(userId)
      .orElseThrow(() -> new CustomSecurityException(
        ApiMessages.LOGISTIC_PERSON_NOT_FOUND + userId,
        HttpStatus.NOT_FOUND));

    return mapper.toResponseDto(entity);
  }

  @Override
  @Transactional
  public LogisticPersonDetailsDTO update(
        Long userId,
        LogisticPersonDetailsDTO dto) {

    var existing = repo.findByUserId(userId)
      .orElseThrow(() -> new CustomSecurityException(
        ApiMessages.LOGISTIC_PERSON_NOT_FOUND + userId,
        HttpStatus.NOT_FOUND));

    // in-place, ignore nulls
    mapper.updateFromDto(dto, existing);

    var updated = repo.save(existing);
    return mapper.toDto(updated);
  }

  @Override
  @Transactional
  public void delete(Long userId) {
    var existing = repo.findByUserId(userId)
      .orElseThrow(() -> new CustomSecurityException(
        ApiMessages.LOGISTIC_PERSON_NOT_FOUND + userId,
        HttpStatus.NOT_FOUND));

    repo.delete(existing);
    // you can choose to return a DTO or simply void; controller can respond with 204 + message
  }
}
