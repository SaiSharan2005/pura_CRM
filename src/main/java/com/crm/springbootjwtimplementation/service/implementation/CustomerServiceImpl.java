
// Customer Service Implementation
package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.Customer;
import com.crm.springbootjwtimplementation.domain.dto.CustomerDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.repository.CustomerRepository;
import com.crm.springbootjwtimplementation.service.CustomerService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CustomerDTO createCustomer(CustomerDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new CustomSecurityException("Customer already exists with email: " + dto.getEmail(), HttpStatus.BAD_REQUEST);
        }
        Customer entity = modelMapper.map(dto, Customer.class);
        entity = repository.save(entity);
        return modelMapper.map(entity, CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer entity = repository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Customer not found with ID: " + id, HttpStatus.NOT_FOUND));
        return modelMapper.map(entity, CustomerDTO.class);
    }

    @Override
    public CustomerDTO updateCustomerById(Long id, CustomerDTO dto) {
        Customer entity = repository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Customer not found with ID: " + id, HttpStatus.NOT_FOUND));
        modelMapper.map(dto, entity);
        Customer updatedEntity = repository.save(entity);
        return modelMapper.map(updatedEntity, CustomerDTO.class);
    }

    @Override
    public void deleteCustomerById(Long id) {
        if (!repository.existsById(id)) {
            throw new CustomSecurityException("Customer not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }
}
