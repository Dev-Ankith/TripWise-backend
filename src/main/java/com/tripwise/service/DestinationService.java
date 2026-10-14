package com.tripwise.service;

import com.tripwise.dto.DestinationDTO;
import com.tripwise.entity.Destination;
import com.tripwise.exception.ResourceNotFoundException;
import com.tripwise.repository.DestinationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @Transactional(readOnly = true)
    public List<DestinationDTO> getAllDestinations() {
        return destinationRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DestinationDTO getDestinationById(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + id));
        return toDTO(destination);
    }

    @Transactional(readOnly = true)
    public List<DestinationDTO> searchDestinationsByName(String name) {
        return destinationRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DestinationDTO createDestination(DestinationDTO dto) {
        Destination destination = new Destination();
        destination.setName(dto.getName());
        destination.setState(dto.getState());
        destination.setDescription(dto.getDescription());
        destination.setImageUrl(dto.getImageUrl());
        destination.setEstimatedStartingCost(dto.getEstimatedStartingCost());
        destination.setTravelCategory(dto.getTravelCategory());

        Destination saved = destinationRepository.save(destination);
        return toDTO(saved);
    }

    public DestinationDTO updateDestination(Long id, DestinationDTO dto) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + id));

        destination.setName(dto.getName());
        destination.setState(dto.getState());
        destination.setDescription(dto.getDescription());
        destination.setImageUrl(dto.getImageUrl());
        destination.setEstimatedStartingCost(dto.getEstimatedStartingCost());
        destination.setTravelCategory(dto.getTravelCategory());

        Destination updated = destinationRepository.save(destination);
        return toDTO(updated);
    }

    public void deleteDestination(Long id) {
        if (!destinationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Destination not found with id: " + id);
        }
        destinationRepository.deleteById(id);
    }

    public DestinationDTO toDTO(Destination destination) {
        return new DestinationDTO(
                destination.getId(),
                destination.getName(),
                destination.getState(),
                destination.getDescription(),
                destination.getImageUrl(),
                destination.getEstimatedStartingCost(),
                destination.getTravelCategory()
        );
    }
}
