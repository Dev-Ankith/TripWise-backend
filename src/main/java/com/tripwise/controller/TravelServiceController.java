package com.tripwise.controller;

import com.tripwise.dto.TravelServiceDTO;
import com.tripwise.service.TravelServiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class TravelServiceController {

    private final TravelServiceService travelServiceService;

    public TravelServiceController(TravelServiceService travelServiceService) {
        this.travelServiceService = travelServiceService;
    }

    @GetMapping
    public ResponseEntity<List<TravelServiceDTO>> getAllServices() {
        return ResponseEntity.ok(travelServiceService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelServiceDTO> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(travelServiceService.getServiceById(id));
    }

    @GetMapping("/destination/{destinationId}")
    public ResponseEntity<List<TravelServiceDTO>> getServicesByDestination(@PathVariable Long destinationId) {
        return ResponseEntity.ok(travelServiceService.getServicesByDestination(destinationId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<TravelServiceDTO>> getServicesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(travelServiceService.getServicesByCategory(category));
    }

    @PostMapping
    public ResponseEntity<TravelServiceDTO> createService(@Valid @RequestBody TravelServiceDTO dto) {
        TravelServiceDTO created = travelServiceService.createService(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelServiceDTO> updateService(@PathVariable Long id, @Valid @RequestBody TravelServiceDTO dto) {
        return ResponseEntity.ok(travelServiceService.updateService(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        travelServiceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
