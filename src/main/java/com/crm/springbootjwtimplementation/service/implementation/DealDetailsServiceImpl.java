package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.Cart;
import com.crm.springbootjwtimplementation.domain.Customer;
import com.crm.springbootjwtimplementation.domain.DealDetails;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.dto.DealDetailsDTO;
import com.crm.springbootjwtimplementation.dto.DealRequest;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.repository.CartRepository;
import com.crm.springbootjwtimplementation.repository.CustomerRepository;
import com.crm.springbootjwtimplementation.repository.DealDetailsRepository;
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
    public DealDetailsDTO createDeal(DealRequest dealRequest) {
        if (Objects.isNull(dealRequest)) {
            throw new CustomSecurityException(ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        // Fetch customer, cart, and salesman (user) - throw exception if not found
        Customer customer = customerRepository.findById(dealRequest.getCustomerId())
                .orElseThrow(() -> new CustomSecurityException(
                        "Customer not found with ID: " + dealRequest.getCustomerId(), HttpStatus.NOT_FOUND));

        Cart cart = cartRepository.findById(dealRequest.getCartId())
                .orElseThrow(() -> new CustomSecurityException(
                        "Cart not found with ID: " + dealRequest.getCartId(), HttpStatus.NOT_FOUND));

        User salesman = userRepository.findById(dealRequest.getUserId())
                .orElseThrow(() -> new CustomSecurityException(
                        "User not found with ID: " + dealRequest.getUserId(), HttpStatus.NOT_FOUND));


        // Map DealRequest to DealDetails and associate the fetched entities.
        DealDetails dealDetails = modelMapper.map(dealRequest, DealDetails.class);


        dealDetails.setCustomer(customer);
        dealDetails.setCart(cart);
        dealDetails.setUser(salesman);

        // Save and return the saved deal as DTO.
        DealDetails savedDeal = dealDetailsRepository.save(dealDetails);
        return modelMapper.map(savedDeal, DealDetailsDTO.class);
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
                .orElseThrow(() -> new CustomSecurityException(ApiMessages.DEAL_NOT_FOUND + id, HttpStatus.NOT_FOUND));
        return modelMapper.map(dealDetails, DealDetailsDTO.class);
    }

    @Override
    public List<DealDetailsDTO> getDealsOfUser(Long userId) {
        // Use JPA query naming convention to find by the user’s id.
        List<DealDetails> deals = dealDetailsRepository.findByUser_Id(userId);
        if (deals.isEmpty()) {
            throw new CustomSecurityException(ApiMessages.USER_HAS_NO_DEALS + userId, HttpStatus.NOT_FOUND);
        }
        return deals.stream()
                .map(deal -> modelMapper.map(deal, DealDetailsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DealDetailsDTO updateDealById(Long id, DealRequest dealRequest) {
        if (Objects.isNull(dealRequest)) {
            throw new CustomSecurityException(ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        DealDetails existingDeal = dealDetailsRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException(ApiMessages.DEAL_NOT_FOUND + id, HttpStatus.NOT_FOUND));

        // Configure the mapping to skip the id field if not intended to be updated.
        modelMapper.typeMap(DealRequest.class, DealDetails.class).addMappings(mapper -> {
            mapper.skip(DealDetails::setId);
        });
        modelMapper.map(dealRequest, existingDeal);

        DealDetails updatedDeal = dealDetailsRepository.save(existingDeal);
        return modelMapper.map(updatedDeal, DealDetailsDTO.class);
    }

    @Override
    public void deleteDealById(Long id) {
        if (!dealDetailsRepository.existsById(id)) {
            throw new CustomSecurityException(ApiMessages.DEAL_NOT_FOUND + id, HttpStatus.NOT_FOUND);
        }
        dealDetailsRepository.deleteById(id);
    }
}
