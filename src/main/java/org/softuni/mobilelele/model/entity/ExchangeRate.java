package org.softuni.mobilelele.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {

    @Id
    @NotNull
    private String currencyCode;

    @NotNull
    private BigDecimal exchangeRate;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public ExchangeRate setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public ExchangeRate setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }
}
