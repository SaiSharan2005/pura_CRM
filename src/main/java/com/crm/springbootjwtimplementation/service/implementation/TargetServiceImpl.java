package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.Target;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.TargetRequestDTO;
import com.crm.springbootjwtimplementation.domain.dto.TargetResponseDTO;
import com.crm.springbootjwtimplementation.repository.TargetRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.TargetService;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TargetServiceImpl implements TargetService {

    @Autowired
    private TargetRepository targetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TargetResponseDTO createTarget(TargetRequestDTO targetRequestDTO) {
        if (Objects.isNull(targetRequestDTO)) {
            throw new RuntimeException(ApiMessages.INVALID_INPUT_DATA);
        }
    
        // Fetch UserDetails entities for assignedTo and assignedBy using the IDs from the DTO
        User assignedTo = userRepository.findById(targetRequestDTO.getAssignedToIds())
                .orElseThrow(() -> new RuntimeException(ApiMessages.USER_NOT_FOUND + targetRequestDTO.getAssignedToIds()));
    
        User assignedBy = userRepository.findById(targetRequestDTO.getAssignedByIds())
                .orElseThrow(() -> new RuntimeException(ApiMessages.USER_NOT_FOUND + targetRequestDTO.getAssignedByIds()));
    
        // Map DTO to entity
        Target target = modelMapper.map(targetRequestDTO, Target.class);
        target.setAssignedToId(assignedTo);
        target.setAssignedById(assignedBy);
    
        // Save the target and map it back to DTO
        target = targetRepository.save(target);
        return modelMapper.map(target, TargetResponseDTO.class);
    }
    
    
    @Override
    public TargetResponseDTO updateTarget(Long id, TargetRequestDTO targetRequestDTO) {
        if (Objects.isNull(targetRequestDTO)) {
            throw new RuntimeException(ApiMessages.INVALID_INPUT_DATA);
        }

        // Fetch existing target
        Target existingTarget = targetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ApiMessages.TARGET_NOT_FOUND + id));

        // Map fields from request to existing entity
        modelMapper.typeMap(TargetRequestDTO.class, Target.class).addMappings(mapper -> {
            mapper.skip(Target::setId); // Skip mapping ID
        });
        modelMapper.map(targetRequestDTO, existingTarget);

        // Update related entities
        if (targetRequestDTO.getAssignedToIds() != null) {
            User assignedTo = userRepository.findById(targetRequestDTO.getAssignedToIds())
                    .orElseThrow(() -> new RuntimeException(ApiMessages.USER_NOT_FOUND + targetRequestDTO.getAssignedToIds()));
            existingTarget.setAssignedToId(assignedTo);
        }

        if (targetRequestDTO.getAssignedByIds() != null) {
            User assignedBy = userRepository.findById(targetRequestDTO.getAssignedByIds())
                    .orElseThrow(() -> new RuntimeException(ApiMessages.USER_NOT_FOUND + targetRequestDTO.getAssignedByIds()));
            existingTarget.setAssignedById(assignedBy);
        }

        // Save updated target
        Target updatedTarget = targetRepository.save(existingTarget);
        return modelMapper.map(updatedTarget, TargetResponseDTO.class);
    }

    @Override
    public void deleteTarget(Long id) {
        if (!targetRepository.existsById(id)) {
            throw new RuntimeException(ApiMessages.TARGET_NOT_FOUND + id);
        }
        targetRepository.deleteById(id);
    }

    @Override
    public TargetResponseDTO getTargetById(Long id) {
        Target target = targetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ApiMessages.TARGET_NOT_FOUND + id));
        return modelMapper.map(target, TargetResponseDTO.class);
    }

    @Override
    public List<TargetResponseDTO> getAllTargets() {
        return targetRepository.findAll().stream()
                .map(target -> modelMapper.map(target, TargetResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TargetResponseDTO> getTargetsByAssignedTo(Long assignedToId) {
        List<Target> targets = targetRepository.findByAssignedToIdId(assignedToId);
        if (targets.isEmpty()) {
            throw new RuntimeException(ApiMessages.TARGET_NOT_FOUND + assignedToId);
        }
        return targets.stream()
                .map(target -> modelMapper.map(target, TargetResponseDTO.class))
                .collect(Collectors.toList());
    }
}
