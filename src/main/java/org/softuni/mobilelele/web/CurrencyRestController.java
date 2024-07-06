package org.softuni.mobilelele.web;

import jakarta.validation.Valid;
import org.softuni.mobilelele.model.dto.ConvertRequestDTO;
import org.softuni.mobilelele.model.dto.MoneyDTO;
import org.softuni.mobilelele.service.CurrencyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyRestController {

    private final CurrencyService currencyService;

    public CurrencyRestController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/api/currency/convert")
    public MoneyDTO convert(@Valid ConvertRequestDTO convertRequestDTO) {

        return this.currencyService.convert(convertRequestDTO);
    }
}
