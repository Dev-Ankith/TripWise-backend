package com.tripwise.dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BudgetSummaryDTO {
    private Long tripId;
    private String tripName;
    private String destinationName;
    private Integer numberOfTravelers;
    private BigDecimal budget;
    private BigDecimal totalEstimatedCost;
    private BigDecimal remainingBudget;
    private boolean isWithinBudget;
    private BigDecimal costPerTraveler;
    private Map<String, BigDecimal> categoryBreakdown = new HashMap<>();
    private String notes;

    public BudgetSummaryDTO() {
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
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

    public BigDecimal getTotalEstimatedCost() {
        return totalEstimatedCost;
    }

    public void setTotalEstimatedCost(BigDecimal totalEstimatedCost) {
        this.totalEstimatedCost = totalEstimatedCost;
    }

    public BigDecimal getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(BigDecimal remainingBudget) {
        this.remainingBudget = remainingBudget;
    }

    public boolean isWithinBudget() {
        return isWithinBudget;
    }

    public void setWithinBudget(boolean withinBudget) {
        isWithinBudget = withinBudget;
    }

    public BigDecimal getCostPerTraveler() {
        return costPerTraveler;
    }

    public void setCostPerTraveler(BigDecimal costPerTraveler) {
        this.costPerTraveler = costPerTraveler;
    }

    public Map<String, BigDecimal> getCategoryBreakdown() {
        return categoryBreakdown;
    }

    public void setCategoryBreakdown(Map<String, BigDecimal> categoryBreakdown) {
        this.categoryBreakdown = categoryBreakdown;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
