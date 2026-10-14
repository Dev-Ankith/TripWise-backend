package com.tripwise.service;

import com.tripwise.dto.BudgetSummaryDTO;
import com.tripwise.dto.TripDTO;
import com.tripwise.dto.TripItemDTO;
import com.tripwise.entity.Destination;
import com.tripwise.entity.TravelService;
import com.tripwise.entity.Trip;
import com.tripwise.entity.TripItem;
import com.tripwise.exception.BadRequestException;
import com.tripwise.exception.ResourceNotFoundException;
import com.tripwise.repository.DestinationRepository;
import com.tripwise.repository.TravelServiceRepository;
import com.tripwise.repository.TripItemRepository;
import com.tripwise.repository.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripService {

    private final TripRepository tripRepository;
    private final TripItemRepository tripItemRepository;
    private final DestinationRepository destinationRepository;
    private final TravelServiceRepository travelServiceRepository;

    public TripService(TripRepository tripRepository,
                       TripItemRepository tripItemRepository,
                       DestinationRepository destinationRepository,
                       TravelServiceRepository travelServiceRepository) {
        this.tripRepository = tripRepository;
        this.tripItemRepository = tripItemRepository;
        this.destinationRepository = destinationRepository;
        this.travelServiceRepository = travelServiceRepository;
    }

    public TripDTO createTrip(TripDTO dto) {
        validateTripDates(dto.getStartDate(), dto.getEndDate());

        Destination destination = destinationRepository.findById(dto.getDestinationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + dto.getDestinationId()));

        Trip trip = new Trip();
        trip.setTripName(dto.getTripName());
        trip.setDestination(destination);
        trip.setStartDate(dto.getStartDate());
        trip.setEndDate(dto.getEndDate());
        trip.setNumberOfTravelers(dto.getNumberOfTravelers());
        trip.setBudget(dto.getBudget());
        trip.setInterests(dto.getInterests());
        trip.setStatus(dto.getStatus() != null ? dto.getStatus() : "PLANNED");
        trip.setTotalEstimatedCost(BigDecimal.ZERO);

        Trip saved = tripRepository.save(trip);
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<TripDTO> getAllTrips() {
        return tripRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TripDTO getTripById(Long id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + id));
        return toDTO(trip);
    }

    public TripDTO updateTrip(Long id, TripDTO dto) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + id));

        validateTripDates(dto.getStartDate(), dto.getEndDate());

        Destination destination = destinationRepository.findById(dto.getDestinationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + dto.getDestinationId()));

        trip.setTripName(dto.getTripName());
        trip.setDestination(destination);
        trip.setStartDate(dto.getStartDate());
        trip.setEndDate(dto.getEndDate());
        trip.setNumberOfTravelers(dto.getNumberOfTravelers());
        trip.setBudget(dto.getBudget());
        if (dto.getInterests() != null) {
            trip.setInterests(dto.getInterests());
        }
        if (dto.getStatus() != null) {
            trip.setStatus(dto.getStatus());
        }

        recalculateTripTotal(trip);
        Trip updated = tripRepository.save(trip);
        return toDTO(updated);
    }

    public void deleteTrip(Long id) {
        if (!tripRepository.existsById(id)) {
            throw new ResourceNotFoundException("Trip not found with id: " + id);
        }
        tripRepository.deleteById(id);
    }

    // Trip Items business logic
    public TripItemDTO addItemToTrip(Long tripId, TripItemDTO itemDTO) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));

        TravelService service = travelServiceRepository.findById(itemDTO.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Travel service not found with id: " + itemDTO.getServiceId()));

        // Validate service destination matches trip destination
        if (service.getDestination() != null && !service.getDestination().getId().equals(trip.getDestination().getId())) {
            throw new BadRequestException("Selected travel service (" + service.getName() +
                    ") does not belong to the trip destination (" + trip.getDestination().getName() + ").");
        }

        if (itemDTO.getQuantity() == null || itemDTO.getQuantity() <= 0) {
            throw new BadRequestException("Item quantity must be greater than 0");
        }

        // Snapshot unit price from the service (or user quote if strictly validated)
        BigDecimal unitPrice = itemDTO.getUnitPrice() != null ? itemDTO.getUnitPrice() : service.getPrice();
        if (unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Unit price cannot be negative");
        }

        // Subtotal calculated strictly on backend: quantity * unit price
        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(itemDTO.getQuantity()));

        TripItem tripItem = new TripItem();
        tripItem.setTrip(trip);
        tripItem.setTravelService(service);
        tripItem.setQuantity(itemDTO.getQuantity());
        tripItem.setUnitPrice(unitPrice);
        tripItem.setSubtotal(subtotal);

        trip.addItem(tripItem);
        TripItem savedItem = tripItemRepository.save(tripItem);

        // Recalculate trip total
        recalculateTripTotal(trip);
        tripRepository.save(trip);

        return toItemDTO(savedItem);
    }

    @Transactional(readOnly = true)
    public List<TripItemDTO> getItemsByTrip(Long tripId) {
        if (!tripRepository.existsById(tripId)) {
            throw new ResourceNotFoundException("Trip not found with id: " + tripId);
        }
        return tripItemRepository.findByTripId(tripId).stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());
    }

    public void removeItemFromTrip(Long tripId, Long itemId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));

        TripItem item = tripItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip item not found with id: " + itemId));

        if (!item.getTrip().getId().equals(tripId)) {
            throw new BadRequestException("Trip item does not belong to trip " + tripId);
        }

        trip.removeItem(item);
        tripItemRepository.delete(item);

        recalculateTripTotal(trip);
        tripRepository.save(trip);
    }

    // Budget Summary Calculation
    @Transactional(readOnly = true)
    public BudgetSummaryDTO getTripBudgetSummary(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));

        List<TripItem> items = tripItemRepository.findByTripId(tripId);

        BigDecimal totalCost = BigDecimal.ZERO;
        Map<String, BigDecimal> categoryMap = new LinkedHashMap<>();

        for (TripItem item : items) {
            totalCost = totalCost.add(item.getSubtotal());
            String cat = (item.getTravelService() != null && item.getTravelService().getCategory() != null)
                    ? item.getTravelService().getCategory().toLowerCase()
                    : "other";
            categoryMap.put(cat, categoryMap.getOrDefault(cat, BigDecimal.ZERO).add(item.getSubtotal()));
        }

        BigDecimal budget = trip.getBudget() != null ? trip.getBudget() : BigDecimal.ZERO;
        BigDecimal remaining = budget.subtract(totalCost);
        boolean isWithin = remaining.compareTo(BigDecimal.ZERO) >= 0;

        int travelers = (trip.getNumberOfTravelers() != null && trip.getNumberOfTravelers() > 0)
                ? trip.getNumberOfTravelers() : 1;
        BigDecimal costPerTraveler = totalCost.divide(BigDecimal.valueOf(travelers), 2, RoundingMode.HALF_UP);

        BudgetSummaryDTO summary = new BudgetSummaryDTO();
        summary.setTripId(trip.getId());
        summary.setTripName(trip.getTripName());
        summary.setDestinationName(trip.getDestination() != null ? trip.getDestination().getName() : "");
        summary.setNumberOfTravelers(travelers);
        summary.setBudget(budget);
        summary.setTotalEstimatedCost(totalCost);
        summary.setRemainingBudget(remaining);
        summary.setWithinBudget(isWithin);
        summary.setCostPerTraveler(costPerTraveler);
        summary.setCategoryBreakdown(categoryMap);

        if (!categoryMap.containsKey("food")) {
            summary.setNotes("Note: Food & personal expenses are not booked as items and are estimated separately.");
        } else {
            summary.setNotes("All selected direct services included.");
        }

        return summary;
    }

    // Helper methods
    private void recalculateTripTotal(Trip trip) {
        List<TripItem> items = tripItemRepository.findByTripId(trip.getId());
        BigDecimal total = BigDecimal.ZERO;
        for (TripItem it : items) {
            total = total.add(it.getSubtotal());
        }
        trip.setTotalEstimatedCost(total);
    }

    private void validateTripDates(java.time.LocalDate startDate, java.time.LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new BadRequestException("Start date and End date must be provided");
        }
        if (endDate.isBefore(startDate)) {
            throw new BadRequestException("End date (" + endDate + ") cannot be before start date (" + startDate + ")");
        }
    }

    public TripDTO toDTO(Trip trip) {
        TripDTO dto = new TripDTO();
        dto.setId(trip.getId());
        dto.setTripName(trip.getTripName());
        if (trip.getDestination() != null) {
            dto.setDestinationId(trip.getDestination().getId());
            dto.setDestinationName(trip.getDestination().getName());
        }
        dto.setStartDate(trip.getStartDate());
        dto.setEndDate(trip.getEndDate());
        dto.setNumberOfTravelers(trip.getNumberOfTravelers());
        dto.setBudget(trip.getBudget());
        dto.setInterests(trip.getInterests());
        dto.setStatus(trip.getStatus());
        dto.setTotalEstimatedCost(trip.getTotalEstimatedCost());

        List<TripItem> items = tripItemRepository.findByTripId(trip.getId());
        dto.setItems(items.stream().map(this::toItemDTO).collect(Collectors.toList()));

        return dto;
    }

    public TripItemDTO toItemDTO(TripItem item) {
        TripItemDTO dto = new TripItemDTO();
        dto.setId(item.getId());
        if (item.getTravelService() != null) {
            dto.setServiceId(item.getTravelService().getId());
            dto.setServiceName(item.getTravelService().getName());
            dto.setCategory(item.getTravelService().getCategory());
            dto.setProviderName(item.getTravelService().getProviderName());
        }
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }
}
