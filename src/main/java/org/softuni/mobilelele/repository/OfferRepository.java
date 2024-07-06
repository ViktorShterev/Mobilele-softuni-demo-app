package org.softuni.mobilelele.repository;

import org.softuni.mobilelele.model.dto.OfferSummaryDTO;
import org.softuni.mobilelele.model.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    Optional<Offer> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
