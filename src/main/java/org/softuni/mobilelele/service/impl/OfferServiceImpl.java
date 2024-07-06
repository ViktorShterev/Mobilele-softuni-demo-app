package org.softuni.mobilelele.service.impl;

import jakarta.transaction.Transactional;
import org.softuni.mobilelele.model.dto.CreateOfferDTO;
import org.softuni.mobilelele.model.dto.OfferSummaryDTO;
import org.softuni.mobilelele.model.entity.Model;
import org.softuni.mobilelele.model.entity.Offer;
import org.softuni.mobilelele.repository.ModelRepository;
import org.softuni.mobilelele.repository.OfferRepository;
import org.softuni.mobilelele.service.OfferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelRepository modelRepository;

    public OfferServiceImpl(OfferRepository offerRepository, ModelRepository modelRepository) {
        this.offerRepository = offerRepository;
        this.modelRepository = modelRepository;
    }

    @Override
    public UUID createOffer(CreateOfferDTO createOfferDTO) {

        Offer offer = map(createOfferDTO);

        Model model = this.modelRepository.findById(createOfferDTO.modelId())
                .orElseThrow(() -> new IllegalArgumentException("Model not found"));

        offer.setModel(model);

        this.offerRepository.save(offer);

        return offer.getUuid();
    }

    @Override
    public Page<OfferSummaryDTO> getAllOffers(Pageable pageable) {
        return this.offerRepository
                .findAll(pageable)
                .map(OfferServiceImpl::mapAsSummary);
    }

    @Override
    public Optional<OfferSummaryDTO> getOfferDetail(UUID id) {
        return this.offerRepository
                .findByUuid(id)
                .map(OfferServiceImpl::mapAsSummary);
    }

    @Override
    @Transactional
    public void deleteOffer(UUID uuid) {
        this.offerRepository.deleteByUuid(uuid);
    }

    private static OfferSummaryDTO mapAsSummary(Offer offer) {
        return new OfferSummaryDTO(
                offer.getUuid().toString(),
                offer.getModel().getBrand().getName(),
                offer.getModel().getName(),
                offer.getYear(),
                (int) offer.getMileage(),
                offer.getImageUrl(),
                offer.getPrice(),
                offer.getEngine(),
                offer.getTransmission()
                );
    }

    private static Offer map(CreateOfferDTO createOfferDTO) {
        return new Offer()
                .setUuid(UUID.randomUUID())
                .setDescription(createOfferDTO.description())
                .setEngine(createOfferDTO.engine())
                .setTransmission(createOfferDTO.transmission())
                .setPrice(createOfferDTO.price())
                .setImageUrl(createOfferDTO.imageUrl())
                .setMileage(createOfferDTO.mileage())
                .setYear(createOfferDTO.year())
                .setCreated(LocalDateTime.now());
    }
}
