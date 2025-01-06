package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.dto.DeliveryRequestDTO;
import com.crm.springbootjwtimplementation.domain.dto.DeliveryResponseDTO;
import com.crm.springbootjwtimplementation.domain.DealDetails;
import com.crm.springbootjwtimplementation.domain.Delivery;
import com.crm.springbootjwtimplementation.domain.LogisticPersonDetails;
import com.crm.springbootjwtimplementation.repository.DealDetailsRepository;
import com.crm.springbootjwtimplementation.repository.DeliveryRepository;
import com.crm.springbootjwtimplementation.repository.LogisticPersonDetailsRepository;
import com.crm.springbootjwtimplementation.service.DeliveryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DealDetailsRepository dealRepository;

    @Autowired
    private LogisticPersonDetailsRepository logisticPersonDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DeliveryResponseDTO createDelivery(DeliveryRequestDTO requestDTO) {
        // Convert DTO to entity
        Delivery delivery = modelMapper.map(requestDTO, Delivery.class);

        // Validate and set Deal
        DealDetails deal = dealRepository.findById(requestDTO.getDealId())
                .orElseThrow(() -> new RuntimeException("Deal not found"));
        delivery.setDeal(deal);

        // Validate and set LogisticPersonDetails
        LogisticPersonDetails logisticPerson = logisticPersonDetailsRepository.findById(requestDTO.getLogisticPersonId())
                .orElseThrow(() -> new RuntimeException("Logistic person not found"));
        delivery.setLogisticPerson(logisticPerson);

        // Save and return the response DTO
        Delivery savedDelivery = deliveryRepository.save(delivery);
        return modelMapper.map(savedDelivery, DeliveryResponseDTO.class);
    }

    @Override
    public DeliveryResponseDTO getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
        return modelMapper.map(delivery, DeliveryResponseDTO.class);
    }

    @Override
    public List<DeliveryResponseDTO> getAllDeliveries() {
        return deliveryRepository.findAll().stream()
                .map(delivery -> modelMapper.map(delivery, DeliveryResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DeliveryResponseDTO> getAllDeliveriesOfUser(Long id) {
        return deliveryRepository.findByLogisticPersonUserId(id).stream()
                .map(delivery -> modelMapper.map(delivery, DeliveryResponseDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public DeliveryResponseDTO updateDelivery(Long id, DeliveryRequestDTO requestDTO) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        // Validate and set Deal
        DealDetails deal = dealRepository.findById(requestDTO.getDealId())
                .orElseThrow(() -> new RuntimeException("Deal not found"));
        delivery.setDeal(deal);

        // Validate and set LogisticPersonDetails
        LogisticPersonDetails logisticPerson = logisticPersonDetailsRepository.findById(requestDTO.getLogisticPersonId())
                .orElseThrow(() -> new RuntimeException("Logistic person not found"));
        delivery.setLogisticPerson(logisticPerson);

        // Update remaining fields
        modelMapper.map(requestDTO, delivery);

        Delivery updatedDelivery = deliveryRepository.save(delivery);
        return modelMapper.map(updatedDelivery, DeliveryResponseDTO.class);
    }

    @Override
    public String deleteDelivery(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
        deliveryRepository.delete(delivery);
        return "Delivery deleted successfully.";
    }
}
