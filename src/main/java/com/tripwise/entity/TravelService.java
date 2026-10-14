package com.tripwise.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "travel_services")
public class TravelService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Service name is required")
    @Column(nullable = false, length = 150)
    private String name;

    /**
     * Categories: accommodation, transportation, local transport, activities, sightseeing, guides, food
     */
    @NotBlank(message = "Category is required")
    @Column(nullable = false, length = 80)
    private String category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Destination destination;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Provider name is required")
    @Column(name = "provider_name", nullable = false, length = 120)
    private String providerName;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be non-negative")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "contact_details", length = 200)
    private String contactDetails;

    @Column(name = "availability_status", length = 50)
    private String availabilityStatus = "AVAILABLE";

    public TravelService() {
    }

    public TravelService(Long id, String name, String category, Destination destination, String description,
                         String providerName, BigDecimal price, Double rating, String contactDetails, String availabilityStatus) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.destination = destination;
        this.description = description;
        this.providerName = providerName;
        this.price = price;
        this.rating = rating;
        this.contactDetails = contactDetails;
        this.availabilityStatus = availabilityStatus;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}
