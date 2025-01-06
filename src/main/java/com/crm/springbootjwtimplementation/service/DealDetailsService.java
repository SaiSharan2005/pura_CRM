// Deal Service
package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.DealDetails;
import com.crm.springbootjwtimplementation.domain.dto.DealDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.DealRequest;

import java.util.List;

public interface DealDetailsService {

    DealDetailsDTO createDeal(DealRequest dealDetailsDTO);

    List<DealDetailsDTO> getAllDeals();

    DealDetailsDTO getDealById(Long id);

    List<DealDetailsDTO> getDealsOfUser(Long id);
    DealDetailsDTO updateDealById(Long id, DealRequest dealDetailsDTO);

    void deleteDealById(Long id);
}