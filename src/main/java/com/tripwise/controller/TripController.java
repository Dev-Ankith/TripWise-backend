package com.tripwise.controller;

import com.tripwise.dto.BudgetSummaryDTO;
import com.tripwise.dto.TripDTO;
import com.tripwise.dto.TripItemDTO;
import com.tripwise.service.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<TripDTO> createTrip(@Valid @RequestBody TripDTO dto) {
        TripDTO created = tripService.createTrip(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TripDTO>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDTO> getTripById(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.getTripById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripDTO> updateTrip(@PathVariable Long id, @Valid @RequestBody TripDTO dto) {
        return ResponseEntity.ok(tripService.updateTrip(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }

    // Trip items sub-endpoints
    @PostMapping("/{tripId}/items")
    public ResponseEntity<TripItemDTO> addItemToTrip(@PathVariable Long tripId, @Valid @RequestBody TripItemDTO itemDTO) {
        TripItemDTO created = tripService.addItemToTrip(tripId, itemDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{tripId}/items")
    public ResponseEntity<List<TripItemDTO>> getItemsByTrip(@PathVariable Long tripId) {
        return ResponseEntity.ok(tripService.getItemsByTrip(tripId));
    }

    @DeleteMapping("/{tripId}/items/{itemId}")
    public ResponseEntity<Void> removeItemFromTrip(@PathVariable Long tripId, @PathVariable Long itemId) {
        tripService.removeItemFromTrip(tripId, itemId);
        return ResponseEntity.noContent().build();
    }

    // Budget endpoint
    @GetMapping("/{tripId}/budget")
    public ResponseEntity<BudgetSummaryDTO> getTripBudget(@PathVariable Long tripId) {
        return ResponseEntity.ok(tripService.getTripBudgetSummary(tripId));
    }
}
