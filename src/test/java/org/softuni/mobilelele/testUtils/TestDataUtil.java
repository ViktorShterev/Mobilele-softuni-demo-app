package org.softuni.mobilelele.testUtils;

import org.softuni.mobilelele.model.entity.ExchangeRate;
import org.softuni.mobilelele.model.entity.Offer;
import org.softuni.mobilelele.model.entity.User;
import org.softuni.mobilelele.model.entity.UserRole;
import org.softuni.mobilelele.model.enums.RolesEnum;
import org.softuni.mobilelele.repository.ExchangeRateRepository;
import org.softuni.mobilelele.repository.OfferRepository;
import org.softuni.mobilelele.repository.UserRepository;
import org.softuni.mobilelele.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TestDataUtil {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private OfferRepository offerRepository;

    public void createExchangeRate(String currency, BigDecimal rate) {
        exchangeRateRepository.save(
                new ExchangeRate().setCurrencyCode(currency).setExchangeRate(rate));
    }

//    public Offer createTestOffer(User owner) {
//
//    }

    public void cleanAllTestData() {
        exchangeRateRepository.deleteAll();
    }
}
