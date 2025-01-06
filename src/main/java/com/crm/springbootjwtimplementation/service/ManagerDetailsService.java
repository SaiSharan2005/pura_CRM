package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.dto.ManagerDetailsDTO;

import java.util.List;

public interface ManagerDetailsService {

    ManagerDetailsDTO createManagerDetails(Long userId,ManagerDetailsDTO managerDetailsDTO);

    List<ManagerDetailsDTO> getAllManagerDetails();

    ManagerDetailsDTO getManagerDetailsById(Long id);

    ManagerDetailsDTO updateManagerDetailsById(Long id, ManagerDetailsDTO managerDetailsDTO);

    void deleteManagerDetailsById(Long id);
}
