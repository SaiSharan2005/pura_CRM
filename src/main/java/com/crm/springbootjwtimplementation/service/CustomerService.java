// Customer Service Interface
package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.dto.CustomerDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.LogisticPersonDetailsResponseDTO;

import java.util.List;

import org.springframework.data.domain.Page;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO dto);

    Page<CustomerDTO> getAllCustomers(int page, int size);

    CustomerDTO getCustomerById(Long id);

    CustomerDTO updateCustomerById(Long id, CustomerDTO dto);

    void deleteCustomerById(Long id);

    Page<CustomerDTO> searchCustomers(String filter, int page, int size);

}
