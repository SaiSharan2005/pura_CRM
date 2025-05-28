package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.dto.CustomerDTO;
import com.crm.springbootjwtimplementation.dto.ResponseMessageDTO;
import com.crm.springbootjwtimplementation.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    /**
     * Create a new customer.
     * POST /api/customers
     */
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(
            @Valid @RequestBody CustomerDTO dto) {

        CustomerDTO created = service.createCustomer(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    /**
     * List customers with pagination.
     * GET /api/customers?page=0&size=20
     */
    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> listCustomers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<CustomerDTO> results = service.getAllCustomers(page, size);
        return ResponseEntity.ok(results);
    }

    /**
     * Fetch a single customer by ID.
     * GET /api/customers/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(
            @PathVariable Long id
    ) {
        CustomerDTO dto = service.getCustomerById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Update an existing customer.
     * PUT /api/customers/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomerById(
            @PathVariable Long id,
            @Valid @RequestBody CustomerDTO dto
    ) {
        CustomerDTO updated = service.updateCustomerById(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a customer by ID.
     * DELETE /api/customers/{userId}
     */



        @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseMessageDTO> deleteCustomerById(
            @PathVariable Long userId
    ) {
        service.deleteCustomerById(userId);

        ResponseMessageDTO response = new ResponseMessageDTO();
        response.setMessage("Customer details for user " + userId + " deleted successfully");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/search")
    public ResponseEntity<Page<CustomerDTO>> search(
            @RequestParam("query") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<CustomerDTO> results = service.searchCustomers(query, page, size);
        return ResponseEntity.ok(results);
    }

}
