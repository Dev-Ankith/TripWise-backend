package com.tripwise.service;

import com.tripwise.dto.PackageCompareRequest;
import com.tripwise.dto.PackageCompareResponse;
import com.tripwise.entity.Trip;
import com.tripwise.entity.TripItem;
import com.tripwise.exception.BadRequestException;
import com.tripwise.repository.TripItemRepository;
import com.tripwise.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PackageCompareService {

    private final TripRepository tripRepository;
    private final TripItemRepository tripItemRepository;

    public PackageCompareService(TripRepository tripRepository, TripItemRepository tripItemRepository) {
        this.tripRepository = tripRepository;
        this.tripItemRepository = tripItemRepository;
    }

    public PackageCompareResponse comparePackage(PackageCompareRequest request) {
        if (request.getPackagePrice() == null || request.getPackagePrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Package price must be provided and cannot be negative");
        }

        BigDecimal selfBuiltCost = BigDecimal.ZERO;
        List<String> selfBuiltItemsList = new ArrayList<>();

        if (request.getTripId() != null) {
            Trip trip = tripRepository.findById(request.getTripId()).orElse(null);
            if (trip != null) {
                selfBuiltCost = trip.getTotalEstimatedCost();
                List<TripItem> items = tripItemRepository.findByTripId(trip.getId());
                for (TripItem it : items) {
                    if (it.getTravelService() != null) {
                        selfBuiltItemsList.add(it.getTravelService().getName() + " (Qty: " + it.getQuantity() + ")");
                    }
                }
            }
        } else if (request.getSelfBuiltCost() != null) {
            selfBuiltCost = request.getSelfBuiltCost();
            selfBuiltItemsList.add("Self-built customized itinerary components (totaling ₹" + selfBuiltCost + ")");
        }

        // Compare price difference
        BigDecimal priceDifference = request.getPackagePrice().subtract(selfBuiltCost);

        Double percentDiff = 0.0;
        if (selfBuiltCost.compareTo(BigDecimal.ZERO) > 0) {
            percentDiff = priceDifference
                    .divide(selfBuiltCost, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .doubleValue();
        }

        // Inclusions comparison
        List<String> matched = new ArrayList<>();
        List<String> unmatched = new ArrayList<>();

        if (request.getIncludedServices() != null) {
            for (String inc : request.getIncludedServices()) {
                String cleanInc = inc.trim();
                if (cleanInc.isEmpty()) continue;

                boolean foundMatch = false;
                for (String itemStr : selfBuiltItemsList) {
                    if (itemStr.toLowerCase().contains(cleanInc.toLowerCase()) ||
                            cleanInc.toLowerCase().contains(itemStr.toLowerCase().split(" \\(")[0])) {
                        foundMatch = true;
                        break;
                    }
                }

                if (foundMatch) {
                    matched.add(cleanInc);
                } else {
                    unmatched.add(cleanInc);
                }
            }
        }

        PackageCompareResponse response = new PackageCompareResponse();
        response.setPackageName(request.getPackageName() != null ? request.getPackageName() : "Travel Agent Package");
        response.setDestination(request.getDestination() != null ? request.getDestination() : "Destination");
        response.setPackagePrice(request.getPackagePrice());
        response.setSelfBuiltPrice(selfBuiltCost);
        response.setPriceDifference(priceDifference);
        response.setPercentageDifference(percentDiff);
        response.setMatchedInclusions(matched);
        response.setUnmatchedInclusions(unmatched);
        response.setSelfBuiltItemsConsidered(selfBuiltItemsList);

        // Academic and transparent disclaimer
        response.setTransparencyDisclaimer(
                "Transparency Note: The calculated price difference reflects direct provider rates versus bundled package pricing. " +
                "Differences may stem from service tier disparities, agency support overhead, premium conveniences, taxes, or unmatched inclusions, " +
                "and should not be interpreted as pure brokerage markup or guaranteed savings."
        );

        return response;
    }
}
