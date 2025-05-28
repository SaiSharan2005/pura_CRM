// Customer Service Interface
package com.crm.springbootjwtimplementation.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.crm.springbootjwtimplementation.dto.CustomerDTO;
import com.crm.springbootjwtimplementation.dto.users.LogisticPersonDetailsResponseDTO;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO dto);

    Page<CustomerDTO> getAllCustomers(int page, int size);

    CustomerDTO getCustomerById(Long id);

    CustomerDTO updateCustomerById(Long id, CustomerDTO dto);

    void deleteCustomerById(Long id);

    Page<CustomerDTO> searchCustomers(String filter, int page, int size);

}
