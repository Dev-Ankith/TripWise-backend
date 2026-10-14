package com.tripwise.repository;

import com.tripwise.entity.TravelService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelServiceRepository extends JpaRepository<TravelService, Long> {
    List<TravelService> findByDestinationId(Long destinationId);
    List<TravelService> findByCategoryIgnoreCase(String category);
    List<TravelService> findByDestinationIdAndCategoryIgnoreCase(Long destinationId, String category);
}
