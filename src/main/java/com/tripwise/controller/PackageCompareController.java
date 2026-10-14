package com.tripwise.controller;

import com.tripwise.dto.PackageCompareRequest;
import com.tripwise.dto.PackageCompareResponse;
import com.tripwise.service.PackageCompareService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/packages")
public class PackageCompareController {

    private final PackageCompareService packageCompareService;

    public PackageCompareController(PackageCompareService packageCompareService) {
        this.packageCompareService = packageCompareService;
    }

    @PostMapping("/compare")
    public ResponseEntity<PackageCompareResponse> comparePackage(@Valid @RequestBody PackageCompareRequest request) {
        PackageCompareResponse response = packageCompareService.comparePackage(request);
        return ResponseEntity.ok(response);
    }
}
