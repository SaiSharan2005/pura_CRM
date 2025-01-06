package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.SalesmanDetails;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.SalesmanDetailsDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.repository.SalesmanDetailsRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.SalesmanDetailsService;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SalesmanDetailsServiceImpl implements SalesmanDetailsService {

    @Autowired
    private SalesmanDetailsRepository salesmanDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SalesmanDetailsDTO createSalesmanDetails(Long userId, SalesmanDetailsDTO salesmanDetailsDTO) {
        if (Objects.isNull(userId) || Objects.isNull(salesmanDetailsDTO)) {
            throw new CustomSecurityException(ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomSecurityException(ApiMessages.USER_NOT_FOUND + userId, HttpStatus.NOT_FOUND));

        salesmanDetailsDTO.setUser(user);
        SalesmanDetails salesmanDetails = modelMapper.map(salesmanDetailsDTO, SalesmanDetails.class);
        salesmanDetails = salesmanDetailsRepository.save(salesmanDetails);

        return modelMapper.map(salesmanDetails, SalesmanDetailsDTO.class);
    }

    @Override
    public List<SalesmanDetailsDTO> getAllSalesmanDetails() {
        return salesmanDetailsRepository.findAll().stream()
                .map(salesman -> modelMapper.map(salesman, SalesmanDetailsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public SalesmanDetailsDTO getSalesmanDetailsById(Long id) {
        if (Objects.isNull(id)) {
            throw new CustomSecurityException(ApiMessages.INVALID_ID, HttpStatus.BAD_REQUEST);
        }

        SalesmanDetails salesmanDetails = salesmanDetailsRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException(ApiMessages.SALESMAN_NOT_FOUND + id, HttpStatus.NOT_FOUND));
        return modelMapper.map(salesmanDetails, SalesmanDetailsDTO.class);
    }

    @Override
    public SalesmanDetailsDTO updateSalesmanDetailsById(Long id, SalesmanDetailsDTO salesmanDetailsDTO) {
        if (Objects.isNull(id) || Objects.isNull(salesmanDetailsDTO)) {
            throw new CustomSecurityException(ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException(ApiMessages.USER_NOT_FOUND + id, HttpStatus.NOT_FOUND));

        salesmanDetailsDTO.setUser(user);
        SalesmanDetails existingSalesman = salesmanDetailsRepository.findByUser(user);

        if (existingSalesman == null) {
            throw new CustomSecurityException(ApiMessages.SALESMAN_NOT_FOUND + id, HttpStatus.NOT_FOUND);
        }

        modelMapper.map(salesmanDetailsDTO, existingSalesman);
        SalesmanDetails updatedSalesman = salesmanDetailsRepository.save(existingSalesman);

        return modelMapper.map(updatedSalesman, SalesmanDetailsDTO.class);
    }

    @Override
    public SalesmanDetailsDTO getSalesmanDetailsByUsername(String username) {
        if (Objects.isNull(username) || username.isEmpty()) {
            throw new CustomSecurityException(ApiMessages.INVALID_USERNAME, HttpStatus.BAD_REQUEST);
        }

        SalesmanDetails salesmanDetails = salesmanDetailsRepository.findByUserUsername(username)
                .orElseThrow(() -> new CustomSecurityException(ApiMessages.SALESMAN_NOT_FOUND, HttpStatus.NOT_FOUND));
        return modelMapper.map(salesmanDetails, SalesmanDetailsDTO.class);
    }

    @Override
    public SalesmanDetailsDTO updateSalesmanDetailsByUsername(String username, SalesmanDetailsDTO salesmanDetailsDTO) {
        if (Objects.isNull(username) || username.isEmpty() || Objects.isNull(salesmanDetailsDTO)) {
            throw new CustomSecurityException(ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }

        SalesmanDetails existingSalesman = salesmanDetailsRepository.findByUserUsername(username)
                .orElseThrow(() -> new CustomSecurityException(ApiMessages.SALESMAN_NOT_FOUND, HttpStatus.NOT_FOUND));

        modelMapper.map(salesmanDetailsDTO, existingSalesman);
        salesmanDetailsRepository.save(existingSalesman);

        return modelMapper.map(existingSalesman, SalesmanDetailsDTO.class);
    }

    @Override
    public void deleteSalesmanDetailsById(Long id) {
        if (Objects.isNull(id)) {
            throw new CustomSecurityException(ApiMessages.INVALID_ID, HttpStatus.BAD_REQUEST);
        }

        if (!salesmanDetailsRepository.existsById(id)) {
            throw new CustomSecurityException(ApiMessages.SALESMAN_NOT_FOUND + id, HttpStatus.NOT_FOUND);
        }

        salesmanDetailsRepository.deleteById(id);
    }
}
