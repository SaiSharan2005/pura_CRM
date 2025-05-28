// package com.crm.springbootjwtimplementation.service;
package com.crm.springbootjwtimplementation.service;

import java.util.List;

import com.crm.springbootjwtimplementation.dto.DeliveryRequestDTO;
import com.crm.springbootjwtimplementation.dto.DeliveryResponseDTO;

public interface DeliveryService {
    DeliveryResponseDTO createDelivery(DeliveryRequestDTO requestDTO);

    DeliveryResponseDTO getDeliveryById(Long id);

    List<DeliveryResponseDTO> getAllDeliveries();

    DeliveryResponseDTO updateDelivery(Long id, DeliveryRequestDTO requestDTO);

    String deleteDelivery(Long id);

    List<DeliveryResponseDTO> getAllDeliveriesOfUser(Long id);

}
