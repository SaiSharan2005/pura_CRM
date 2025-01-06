package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.dto.LogisticPersonDetailsDTO;

import java.util.List;

public interface LogisticPersonDetailsService {

    LogisticPersonDetailsDTO createLogisticPersonDetails(Long userId, LogisticPersonDetailsDTO dto);

    List<LogisticPersonDetailsDTO> getAllLogisticPersons();

    LogisticPersonDetailsDTO getLogisticPersonById(Long id);

    LogisticPersonDetailsDTO updateLogisticPersonById(Long id, LogisticPersonDetailsDTO dto);

    void deleteLogisticPersonById(Long id);
}
