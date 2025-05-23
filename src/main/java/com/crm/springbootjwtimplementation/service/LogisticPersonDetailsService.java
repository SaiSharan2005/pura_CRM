package com.crm.springbootjwtimplementation.service;

import org.springframework.data.domain.Page;

import com.crm.springbootjwtimplementation.domain.dto.users.LogisticPersonDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.LogisticPersonDetailsResponseDTO;
public interface LogisticPersonDetailsService {

  LogisticPersonDetailsDTO create(Long userId,
      LogisticPersonDetailsDTO dto);

  Page<LogisticPersonDetailsResponseDTO> list(int page, int size);

  LogisticPersonDetailsResponseDTO getByUserId(Long userId);

  LogisticPersonDetailsDTO update(Long userId,
      LogisticPersonDetailsDTO dto);

  void delete(Long userId);
}
