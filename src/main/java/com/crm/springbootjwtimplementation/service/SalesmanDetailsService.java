package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.dto.SalesmanDetailsDTO;

import java.util.List;

public interface SalesmanDetailsService {
    SalesmanDetailsDTO createSalesmanDetails(Long userid , SalesmanDetailsDTO salesmanDetailsDTO);

    List<SalesmanDetailsDTO> getAllSalesmanDetails();

    SalesmanDetailsDTO getSalesmanDetailsById(Long id);

    SalesmanDetailsDTO updateSalesmanDetailsById(Long id, SalesmanDetailsDTO salesmanDetailsDTO);

    SalesmanDetailsDTO getSalesmanDetailsByUsername(String username);

    SalesmanDetailsDTO updateSalesmanDetailsByUsername(String username, SalesmanDetailsDTO salesmanDetailsDTO);

    void deleteSalesmanDetailsById(Long id);
}
