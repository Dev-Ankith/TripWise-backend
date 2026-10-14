package com.tripwise.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PackageCompareResponse {
    private String packageName;
    private String destination;
    private BigDecimal packagePrice;
    private BigDecimal selfBuiltPrice;
    private BigDecimal priceDifference;
    private Double percentageDifference;
    private List<String> matchedInclusions = new ArrayList<>();
    private List<String> unmatchedInclusions = new ArrayList<>();
    private List<String> selfBuiltItemsConsidered = new ArrayList<>();
    private String transparencyDisclaimer;

    public PackageCompareResponse() {
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

    public BigDecimal getSelfBuiltPrice() {
        return selfBuiltPrice;
    }

    public void setSelfBuiltPrice(BigDecimal selfBuiltPrice) {
        this.selfBuiltPrice = selfBuiltPrice;
    }

    public BigDecimal getPriceDifference() {
        return priceDifference;
    }

    public void setPriceDifference(BigDecimal priceDifference) {
        this.priceDifference = priceDifference;
    }

    public Double getPercentageDifference() {
        return percentageDifference;
    }

    public void setPercentageDifference(Double percentageDifference) {
        this.percentageDifference = percentageDifference;
    }

    public List<String> getMatchedInclusions() {
        return matchedInclusions;
    }

    public void setMatchedInclusions(List<String> matchedInclusions) {
        this.matchedInclusions = matchedInclusions;
    }

    public List<String> getUnmatchedInclusions() {
        return unmatchedInclusions;
    }

    public void setUnmatchedInclusions(List<String> unmatchedInclusions) {
        this.unmatchedInclusions = unmatchedInclusions;
    }

    public List<String> getSelfBuiltItemsConsidered() {
        return selfBuiltItemsConsidered;
    }

    public void setSelfBuiltItemsConsidered(List<String> selfBuiltItemsConsidered) {
        this.selfBuiltItemsConsidered = selfBuiltItemsConsidered;
    }

    public String getTransparencyDisclaimer() {
        return transparencyDisclaimer;
    }

    public void setTransparencyDisclaimer(String transparencyDisclaimer) {
        this.transparencyDisclaimer = transparencyDisclaimer;
    }
}
