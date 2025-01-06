// Customer Service Interface
package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO dto);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO updateCustomerById(Long id, CustomerDTO dto);

    void deleteCustomerById(Long id);
}
