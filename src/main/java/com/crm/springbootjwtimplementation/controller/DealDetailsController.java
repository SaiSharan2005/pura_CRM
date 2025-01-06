// Deal Controller
package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.domain.DealDetails;
import com.crm.springbootjwtimplementation.domain.dto.DealDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.DealRequest;
import com.crm.springbootjwtimplementation.domain.dto.TokenResponseDTO;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.service.DealDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deals")
public class DealDetailsController {

    @Autowired
    private DealDetailsService dealDetailsService;
    @Autowired
    private AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<DealDetailsDTO> createDeal( @Validated @RequestBody DealRequest dealDetailsDTO) {
        // System.out.println("temp");
        // return ResponseEntity.status(HttpStatus.CREATED).body("test");
        return ResponseEntity.status(HttpStatus.CREATED).body(dealDetailsService.createDeal(dealDetailsDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DealDetailsDTO>> getAllDeals() {
        return ResponseEntity.ok(dealDetailsService.getAllDeals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealDetailsDTO> getDealsOfUser(@PathVariable Long id) {

        return ResponseEntity.ok(dealDetailsService.getDealById(id));
    }  
    @GetMapping("/")
    public ResponseEntity<List<DealDetailsDTO>> getDealsOfUser() {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        return ResponseEntity.ok(dealDetailsService.getDealsOfUser(userToken.getId()));
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<DealDetailsDTO> updateDealById(@PathVariable Long id, @Validated @RequestBody DealRequest dealDetailsDTO) {
        return ResponseEntity.ok(dealDetailsService.updateDealById(id, dealDetailsDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDealById(@PathVariable Long id) {
        dealDetailsService.deleteDealById(id);
        return ResponseEntity.noContent().build();
    }
}
 