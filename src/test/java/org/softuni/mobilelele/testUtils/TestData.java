package org.softuni.mobilelele.testUtils;

import org.softuni.mobilelele.model.entity.ExchangeRate;
import org.softuni.mobilelele.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TestData {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public void createExchangeRate(String currency, BigDecimal rate) {

        exchangeRateRepository.save(
                new ExchangeRate().setCurrencyCode(currency).setExchangeRate(rate));
    }

    public void cleanAllTestData() {
        exchangeRateRepository.deleteAll();
    }
}
