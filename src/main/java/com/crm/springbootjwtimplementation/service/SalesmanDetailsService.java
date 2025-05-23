// SalesmanDetailsService.java
package com.crm.springbootjwtimplementation.service;

import org.springframework.data.domain.Page;

import com.crm.springbootjwtimplementation.domain.dto.users.*;

public interface SalesmanDetailsService {
    SalesmanDetailsResponseDTO createSalesmanDetails(Long userId, SalesmanDetailsDTO dto);
    Page<SalesmanDetailsResponseDTO> getAllSalesmanDetails(int page, int size);
    SalesmanDetailsResponseDTO getSalesmanDetailsByUserId(Long userId);
    SalesmanDetailsResponseDTO updateSalesmanDetailsByUserId(Long userId, SalesmanDetailsDTO dto);
    void deleteSalesmanDetailsByUserId(Long userId);
    SalesmanDetailsResponseDTO getSalesmanDetailsByUsername(String username);
    SalesmanDetailsResponseDTO updateSalesmanDetailsByUsername(String username, SalesmanDetailsDTO dto);
}
