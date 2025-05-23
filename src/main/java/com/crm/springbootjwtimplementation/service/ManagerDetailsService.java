package com.crm.springbootjwtimplementation.service;

import org.springframework.data.domain.Page;
import com.crm.springbootjwtimplementation.domain.dto.users.ManagerDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.ManagerDetailsResponseDTO;

public interface ManagerDetailsService {

    /**
     * Create a new ManagerDetails record.
     * @param dto incoming data (must contain userId)
     * @return newly created record as a ResponseDTO
     */
    ManagerDetailsDTO createManagerDetails(ManagerDetailsDTO dto);

    /**
     * List all ManagerDetails in a paginated fashion.
     * @param page zero‑based page index
     * @param size page size (must be >= 1)
     * @return a page of ManagerDetailsResponseDTO
     */
    Page<ManagerDetailsResponseDTO> getAllManagerDetails(int page, int size);

    /**
     * Fetch the ManagerDetails for a given user.
     * @param userId the ID of the user owning the manager record
     * @return the matching ManagerDetailsResponseDTO
     */
    ManagerDetailsResponseDTO getManagerDetailsByUserId(Long userId);

    /**
     * Update the ManagerDetails for a given user.
     * @param userId the ID of the user owning the manager record
     * @param dto the updated fields
     * @return the updated ManagerDetailsResponseDTO
     */
    ManagerDetailsDTO updateManagerDetailsByUserId(
        Long userId,
        ManagerDetailsDTO dto
    );

    /**
     * Delete the ManagerDetails for a given user.
     * @param userId the ID of the user whose manager record is to be deleted
     */
    void deleteManagerDetailsByUserId(Long userId);
}
