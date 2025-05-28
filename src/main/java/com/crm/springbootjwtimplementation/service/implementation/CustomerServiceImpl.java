package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.dto.CustomerDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.mapper.CustomerMapper;
import com.crm.springbootjwtimplementation.repository.CustomerRepository;
import com.crm.springbootjwtimplementation.service.CustomerService;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;

import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper     mapper;

    public CustomerServiceImpl(CustomerRepository repository,
                               CustomerMapper mapper) {
        this.repository = repository;
        this.mapper     = mapper;
    }

    @Override
    @Transactional
    public CustomerDTO createCustomer(CustomerDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new CustomSecurityException(
                ApiMessages.USER_ALREADY_EXISTS + dto.getEmail(),
                HttpStatus.BAD_REQUEST);
        }
        var saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDTO> getAllCustomers(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new CustomSecurityException(
                ApiMessages.INVALID_INPUT_DATA,
                HttpStatus.BAD_REQUEST);
        }
        Pageable pg = PageRequest.of(page, size, Sort.by("id").ascending());
        return repository.findAll(pg)
                         .map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Long id) {
        var entity = repository.findById(id)
            .orElseThrow(() -> new CustomSecurityException(
                "Customer not found with ID: " + id,
                HttpStatus.NOT_FOUND));
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public CustomerDTO updateCustomerById(Long id, CustomerDTO dto) {
        var existing = repository.findById(id)
            .orElseThrow(() -> new CustomSecurityException(
                "Customer not found with ID: " + id,
                HttpStatus.NOT_FOUND));
        // copy non-null properties; you can add an updateFromDto() in the mapper
        mapper.updateFromDto(dto,existing);
    var updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteCustomerById(Long id) {
        if (!repository.existsById(id)) {
            throw new CustomSecurityException(
                "Customer not found with ID: " + id,
                HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDTO> searchCustomers(String filter, int page, int size) {
        if (page < 0 || size <= 0) {
            throw new CustomSecurityException(
                ApiMessages.INVALID_INPUT_DATA,
                HttpStatus.BAD_REQUEST);
        }
        Pageable pg = PageRequest.of(page, size, Sort.by("customerName").ascending());
        return repository
            .findByCustomerNameContainingIgnoreCaseOrBuyerCompanyNameContainingIgnoreCase(
                filter, filter, pg
            )
            .map(mapper::toDto);
    }

}
