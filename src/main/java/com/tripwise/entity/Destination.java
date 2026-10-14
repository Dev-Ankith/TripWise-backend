package com.tripwise.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "destinations")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Destination name is required")
    @Column(nullable = false, length = 120)
    private String name;

    @NotBlank(message = "State is required")
    @Column(nullable = false, length = 100)
    private String state;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @NotNull(message = "Estimated starting cost is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Estimated starting cost must be non-negative")
    @Column(name = "estimated_starting_cost", precision = 12, scale = 2)
    private BigDecimal estimatedStartingCost;

    @NotBlank(message = "Travel category is required")
    @Column(name = "travel_category", length = 80)
    private String travelCategory;

    public Destination() {
    }

    public Destination(Long id, String name, String state, String description, String imageUrl, BigDecimal estimatedStartingCost, String travelCategory) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.description = description;
        this.imageUrl = imageUrl;
        this.estimatedStartingCost = estimatedStartingCost;
        this.travelCategory = travelCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getEstimatedStartingCost() {
        return estimatedStartingCost;
    }

    public void setEstimatedStartingCost(BigDecimal estimatedStartingCost) {
        this.estimatedStartingCost = estimatedStartingCost;
    }

    public String getTravelCategory() {
        return travelCategory;
    }

    public void setTravelCategory(String travelCategory) {
        this.travelCategory = travelCategory;
    }
}
