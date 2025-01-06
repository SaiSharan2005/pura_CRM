// Deal Service Implementation
package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.Cart;
import com.crm.springbootjwtimplementation.domain.Customer;
import com.crm.springbootjwtimplementation.domain.DealDetails;
import com.crm.springbootjwtimplementation.domain.SalesmanDetails;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.DealDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.DealRequest;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.repository.CartRepository;
import com.crm.springbootjwtimplementation.repository.CustomerRepository;
import com.crm.springbootjwtimplementation.repository.DealDetailsRepository;
import com.crm.springbootjwtimplementation.repository.SalesmanDetailsRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.DealDetailsService;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DealDetailsServiceImpl implements DealDetailsService {

    @Autowired
    private DealDetailsRepository dealDetailsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DealDetailsDTO createDeal(DealRequest dealDetailsReq) {
        // Check if dealDetailsReq is not null
        if (Objects.isNull(dealDetailsReq)) {
            throw new CustomSecurityException(ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        // Fetch customer, cart, and salesman, throw exception if not found
        Customer customer = customerRepository.findById(dealDetailsReq.getCustomerId())
                .orElseThrow(() -> new CustomSecurityException(
                        "Customer not found with ID: " + dealDetailsReq.getCustomerId(), HttpStatus.NOT_FOUND));

        Cart cart = cartRepository.findById(dealDetailsReq.getCartId())
                .orElseThrow(() -> new CustomSecurityException(
                        "Cart not found with ID: " + dealDetailsReq.getCartId(), HttpStatus.NOT_FOUND));

        User salesman = userRepository.findById(dealDetailsReq.getUserId())
                .orElseThrow(() -> new CustomSecurityException(
                        "User not found with ID: " + dealDetailsReq.getUserId(), HttpStatus.NOT_FOUND));

        // Map DealRequest to DealDetails entity and associate with fetched entities
        DealDetails dealDetails = modelMapper.map(dealDetailsReq, DealDetails.class);
        dealDetails.setCustomerId(customer);
        dealDetails.setCartId(cart);
        dealDetails.setUserId(salesman);

        // Save the deal details and map to DTO
        dealDetails = dealDetailsRepository.save(dealDetails);
        // System.out.println(dealDetails);
        return modelMapper.map(dealDetails, DealDetailsDTO.class);
        // return dealDetails;
    }

    @Override
    public List<DealDetailsDTO> getAllDeals() {
        return dealDetailsRepository.findAll().stream()
                .map(deal -> modelMapper.map(deal, DealDetailsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DealDetailsDTO getDealById(Long id) {
        DealDetails dealDetails = dealDetailsRepository.findById(id)
                .orElseThrow(
                        () -> new CustomSecurityException(ApiMessages.MANAGER_NOT_FOUND + id, HttpStatus.NOT_FOUND));
        return modelMapper.map(dealDetails, DealDetailsDTO.class);
    }
    @Override
    public List<DealDetailsDTO> getDealsOfUser(Long id) {
        List<DealDetails> dealDetails = dealDetailsRepository.findByUserIdId(id);
        if (dealDetails.isEmpty()) {
            throw new CustomSecurityException(ApiMessages.USER_HAS_NO_DEALS + id, HttpStatus.NOT_FOUND);
        }
        return dealDetails.stream()
                .map(deal -> modelMapper.map(deal, DealDetailsDTO.class))
                .collect(Collectors.toList());
    }
    
    
    @Override
    public DealDetailsDTO updateDealById(Long id, DealRequest dealDetailsDTO) {
        if (Objects.isNull(dealDetailsDTO)) {
            throw new CustomSecurityException(ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        // Fetch the existing deal from the database
        DealDetails existingDeal = dealDetailsRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException(
                        ApiMessages.DEAL_NOT_FOUND + id, HttpStatus.NOT_FOUND));

        // Map the fields from the request to the existing entity, excluding the `id`
        // field
        modelMapper.typeMap(DealRequest.class, DealDetails.class).addMappings(mapper -> {
            mapper.skip(DealDetails::setId); // Ensure the `id` field is not mapped
        });

        modelMapper.map(dealDetailsDTO, existingDeal);

        // Save the updated entity
        DealDetails updatedDeal = dealDetailsRepository.save(existingDeal);

        // Convert the updated entity to DTO and return
        return modelMapper.map(updatedDeal, DealDetailsDTO.class);
    }

    @Override
    public void deleteDealById(Long id) {
        if (!dealDetailsRepository.existsById(id)) {
            throw new CustomSecurityException(ApiMessages.MANAGER_NOT_FOUND + id, HttpStatus.NOT_FOUND);
        }

        dealDetailsRepository.deleteById(id);
    }
}