package com.tripwise.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class PackageCompareRequest {
    private String packageName;
    private String destination;

    @NotNull(message = "Package price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Package price must be non-negative")
    private BigDecimal packagePrice;

    private List<String> includedServices;

    private Long tripId;

    @DecimalMin(value = "0.0", inclusive = true, message = "Self built cost must be non-negative")
    private BigDecimal selfBuiltCost;

    public PackageCompareRequest() {
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(BigDecimal packagePrice) {
        this.packagePrice = packagePrice;
    }

    public List<String> getIncludedServices() {
        return includedServices;
    }

    public void setIncludedServices(List<String> includedServices) {
        this.includedServices = includedServices;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public BigDecimal getSelfBuiltCost() {
        return selfBuiltCost;
    }

    public void setSelfBuiltCost(BigDecimal selfBuiltCost) {
        this.selfBuiltCost = selfBuiltCost;
    }
}
