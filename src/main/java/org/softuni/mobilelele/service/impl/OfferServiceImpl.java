package org.softuni.mobilelele.service.impl;

import jakarta.transaction.Transactional;
import org.softuni.mobilelele.model.dto.CreateOfferDTO;
import org.softuni.mobilelele.model.dto.OfferSummaryDTO;
import org.softuni.mobilelele.model.entity.Model;
import org.softuni.mobilelele.model.entity.Offer;
import org.softuni.mobilelele.model.entity.User;
import org.softuni.mobilelele.model.entity.UserRole;
import org.softuni.mobilelele.model.enums.RolesEnum;
import org.softuni.mobilelele.repository.ModelRepository;
import org.softuni.mobilelele.repository.OfferRepository;
import org.softuni.mobilelele.repository.UserRepository;
import org.softuni.mobilelele.service.MonitoringService;
import org.softuni.mobilelele.service.OfferService;
import org.softuni.mobilelele.service.aop.WarnIfExecutionExceeds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelRepository modelRepository;
    private final MonitoringService monitoringService;
    private final UserRepository userRepository;

    public OfferServiceImpl(OfferRepository offerRepository, ModelRepository modelRepository, MonitoringService monitoringService, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.modelRepository = modelRepository;
        this.monitoringService = monitoringService;
        this.userRepository = userRepository;
    }

    @Override
    public UUID createOffer(CreateOfferDTO createOfferDTO, UserDetails seller) {

        Offer offer = map(createOfferDTO);

        Model model = this.modelRepository.findById(createOfferDTO.modelId())
                .orElseThrow(() -> new IllegalArgumentException("Model not found"));

        offer.setModel(model);

        User userSeller = this.userRepository.findByEmail(seller.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        offer.setSeller(userSeller);

        this.offerRepository.save(offer);

        return offer.getUuid();
    }

    @WarnIfExecutionExceeds(timeInMillis = 1000L)
    @Override
    public Page<OfferSummaryDTO> getAllOffers(Pageable pageable, UserDetails viewer) {

        return this.offerRepository
                .findAll(pageable)
                .map(offer -> this.mapAsSummary(offer, viewer));
    }

    @WarnIfExecutionExceeds(timeInMillis = 500L)
    @Override
    public Optional<OfferSummaryDTO> getOfferDetail(UUID id, UserDetails viewer) {
        return this.offerRepository
                .findByUuid(id)
                .map(offer -> this.mapAsSummary(offer, viewer));
    }

    @Override
    @Transactional
    public void deleteOffer(UUID uuid) {
        this.offerRepository.deleteByUuid(uuid);
    }

    @Override
    public boolean isOwner(UUID uuid, String username) {

        Offer offer = this.offerRepository.findByUuid(uuid)
                .orElse(null);

        return isOwner(offer, username);
    }

    private OfferSummaryDTO mapAsSummary(Offer offer, UserDetails viewer) {
        return new OfferSummaryDTO(
                offer.getUuid().toString(),
                offer.getModel().getBrand().getName(),
                offer.getModel().getName(),
                offer.getYear(),
                (int) offer.getMileage(),
                offer.getImageUrl(),
                offer.getPrice(),
                offer.getEngine(),
                offer.getTransmission(),
                offer.getSeller().getFirstName() + " " + offer.getSeller().getLastName(),
                isOwner(offer, viewer != null ? viewer.getUsername() : null),
                offer.getCreated().toString()
        );
    }

    private boolean isOwner(Offer offer, String username) {

        if (offer == null || username == null) {
//          anonymous users own no offers
//          missing offers are meaningless
            return false;
        }

        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (isAdmin(user)) {
            //all admins own all offers
            return true;
        }

        return user.getId().equals(offer.getSeller().getId());
    }

    private boolean isAdmin(User user) {
        return user.getRoles()
                .stream()
                .map(UserRole::getRole)
                .anyMatch(r -> RolesEnum.ADMIN == r);
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
