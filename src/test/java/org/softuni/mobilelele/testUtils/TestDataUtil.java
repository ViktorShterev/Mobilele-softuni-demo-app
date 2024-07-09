package org.softuni.mobilelele.testUtils;

import org.softuni.mobilelele.model.entity.*;
import org.softuni.mobilelele.model.enums.EngineEnum;
import org.softuni.mobilelele.model.enums.ModelCategoryEnum;
import org.softuni.mobilelele.model.enums.TransmissionEnum;
import org.softuni.mobilelele.repository.BrandRepository;
import org.softuni.mobilelele.repository.ExchangeRateRepository;
import org.softuni.mobilelele.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class TestDataUtil {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private BrandRepository brandRepository;

    public void createExchangeRate(String currency, BigDecimal rate) {
        this.exchangeRateRepository.save(
                new ExchangeRate().setCurrencyCode(currency).setExchangeRate(rate));
    }

    public Offer createTestOffer(User owner) {

        Brand brand = new Brand()
                .setName("Test Brand")
                .setModels(
                        List.of(
                                new Model()
                                        .setName("Test Model")
                                        .setCategory(ModelCategoryEnum.CAR)
                        )
                );

        this.brandRepository.save(brand);

        Offer offer = new Offer()
                .setModel(brand.getModels().get(0))
                .setImageUrl("https://google.com")
                .setPrice(BigDecimal.valueOf(1000))
                .setYear(2010)
                .setDescription("Test Description")
                .setEngine(EngineEnum.DIESEL)
                .setTransmission(TransmissionEnum.MANUAL)
                .setMileage(100000)
                .setSeller(owner)
                .setUuid(UUID.randomUUID());

        this.offerRepository.save(offer);

        return offer;
    }

    public void cleanUp() {
        this.exchangeRateRepository.deleteAll();
        this.offerRepository.deleteAll();
        this.brandRepository.deleteAll();
    }
}
