package com.tripwise.service;

import com.tripwise.dto.TravelServiceDTO;
import com.tripwise.entity.Destination;
import com.tripwise.entity.TravelService;
import com.tripwise.exception.ResourceNotFoundException;
import com.tripwise.repository.DestinationRepository;
import com.tripwise.repository.TravelServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TravelServiceService {

    private final TravelServiceRepository travelServiceRepository;
    private final DestinationRepository destinationRepository;

    public TravelServiceService(TravelServiceRepository travelServiceRepository, DestinationRepository destinationRepository) {
        this.travelServiceRepository = travelServiceRepository;
        this.destinationRepository = destinationRepository;
    }

    @Transactional(readOnly = true)
    public List<TravelServiceDTO> getAllServices() {
        return travelServiceRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TravelServiceDTO getServiceById(Long id) {
        TravelService service = travelServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Travel service not found with id: " + id));
        return toDTO(service);
    }

    @Transactional(readOnly = true)
    public List<TravelServiceDTO> getServicesByDestination(Long destinationId) {
        if (!destinationRepository.existsById(destinationId)) {
            throw new ResourceNotFoundException("Destination not found with id: " + destinationId);
        }
        return travelServiceRepository.findByDestinationId(destinationId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TravelServiceDTO> getServicesByCategory(String category) {
        return travelServiceRepository.findByCategoryIgnoreCase(category).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TravelServiceDTO createService(TravelServiceDTO dto) {
        Destination destination = destinationRepository.findById(dto.getDestinationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + dto.getDestinationId()));

        TravelService service = new TravelService();
        service.setName(dto.getName());
        service.setCategory(dto.getCategory().toLowerCase().trim());
        service.setDestination(destination);
        service.setDescription(dto.getDescription());
        service.setProviderName(dto.getProviderName());
        service.setPrice(dto.getPrice());
        service.setRating(dto.getRating() != null ? dto.getRating() : 4.5);
        service.setContactDetails(dto.getContactDetails());
        service.setAvailabilityStatus(dto.getAvailabilityStatus() != null ? dto.getAvailabilityStatus() : "AVAILABLE");

        TravelService saved = travelServiceRepository.save(service);
        return toDTO(saved);
    }

    public TravelServiceDTO updateService(Long id, TravelServiceDTO dto) {
        TravelService service = travelServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Travel service not found with id: " + id));

        Destination destination = destinationRepository.findById(dto.getDestinationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + dto.getDestinationId()));

        service.setName(dto.getName());
        service.setCategory(dto.getCategory().toLowerCase().trim());
        service.setDestination(destination);
        service.setDescription(dto.getDescription());
        service.setProviderName(dto.getProviderName());
        service.setPrice(dto.getPrice());
        if (dto.getRating() != null) {
            service.setRating(dto.getRating());
        }
        service.setContactDetails(dto.getContactDetails());
        if (dto.getAvailabilityStatus() != null) {
            service.setAvailabilityStatus(dto.getAvailabilityStatus());
        }

        TravelService updated = travelServiceRepository.save(service);
        return toDTO(updated);
    }

    public void deleteService(Long id) {
        if (!travelServiceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Travel service not found with id: " + id);
        }
        travelServiceRepository.deleteById(id);
    }

    public TravelServiceDTO toDTO(TravelService service) {
        return new TravelServiceDTO(
                service.getId(),
                service.getName(),
                service.getCategory(),
                service.getDestination() != null ? service.getDestination().getId() : null,
                service.getDestination() != null ? service.getDestination().getName() : null,
                service.getDescription(),
                service.getProviderName(),
                service.getPrice(),
                service.getRating(),
                service.getContactDetails(),
                service.getAvailabilityStatus()
        );
    }
}
