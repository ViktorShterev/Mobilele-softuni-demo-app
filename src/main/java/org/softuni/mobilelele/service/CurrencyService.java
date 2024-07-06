package org.softuni.mobilelele.service;

import org.softuni.mobilelele.model.dto.ConvertRequestDTO;
import org.softuni.mobilelele.model.dto.ExchangeRateDTO;
import org.softuni.mobilelele.model.dto.MoneyDTO;

public interface CurrencyService {

    void refreshRates(ExchangeRateDTO exchangeRateDTO);

    MoneyDTO convert(ConvertRequestDTO convertRequestDTO);
}
