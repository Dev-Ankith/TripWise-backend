package com.tripwise.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Trip name is required")
    @Column(name = "trip_name", nullable = false, length = 150)
    private String tripName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Destination destination;

    @NotNull(message = "Start date is required")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull(message = "Number of travelers is required")
    @Min(value = 1, message = "Number of travelers must be at least 1")
    @Column(name = "number_of_travelers", nullable = false)
    private Integer numberOfTravelers;

    @NotNull(message = "Budget is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Budget must be non-negative")
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal budget;

    @Column(length = 255)
    private String interests;

    @Column(length = 50)
    private String status = "PLANNED";

    @Column(name = "total_estimated_cost", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalEstimatedCost = BigDecimal.ZERO;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<TripItem> items = new ArrayList<>();

    public Trip() {
    }

    public Trip(Long id, String tripName, Destination destination, LocalDate startDate, LocalDate endDate,
                Integer numberOfTravelers, BigDecimal budget, String interests, String status, BigDecimal totalEstimatedCost) {
        this.id = id;
        this.tripName = tripName;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfTravelers = numberOfTravelers;
        this.budget = budget;
        this.interests = interests;
        this.status = status != null ? status : "PLANNED";
        this.totalEstimatedCost = totalEstimatedCost != null ? totalEstimatedCost : BigDecimal.ZERO;
    }

    public void addItem(TripItem item) {
        items.add(item);
        item.setTrip(this);
    }

    public void removeItem(TripItem item) {
        items.remove(item);
        item.setTrip(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getNumberOfTravelers() {
        return numberOfTravelers;
    }

    public void setNumberOfTravelers(Integer numberOfTravelers) {
        this.numberOfTravelers = numberOfTravelers;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalEstimatedCost() {
        return totalEstimatedCost;
    }

    public void setTotalEstimatedCost(BigDecimal totalEstimatedCost) {
        this.totalEstimatedCost = totalEstimatedCost;
    }

    public List<TripItem> getItems() {
        return items;
    }

    public void setItems(List<TripItem> items) {
        this.items = items;
    }
}
