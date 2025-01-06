// package com.crm.springbootjwtimplementation.service;
package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.dto.DeliveryRequestDTO;
import com.crm.springbootjwtimplementation.domain.dto.DeliveryResponseDTO;

import java.util.List;

public interface DeliveryService {
    DeliveryResponseDTO createDelivery(DeliveryRequestDTO requestDTO);

    DeliveryResponseDTO getDeliveryById(Long id);

    List<DeliveryResponseDTO> getAllDeliveries();

    DeliveryResponseDTO updateDelivery(Long id, DeliveryRequestDTO requestDTO);

    String deleteDelivery(Long id);

    List<DeliveryResponseDTO> getAllDeliveriesOfUser(Long id);

}
