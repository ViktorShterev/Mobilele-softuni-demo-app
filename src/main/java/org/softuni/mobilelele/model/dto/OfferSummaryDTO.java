package org.softuni.mobilelele.model.dto;

import org.softuni.mobilelele.model.enums.EngineEnum;
import org.softuni.mobilelele.model.enums.TransmissionEnum;

import java.math.BigDecimal;

public record OfferSummaryDTO(
        String uuid,
        String brand,
        String model,
        int year,
        int mileage,
        String image,
        BigDecimal price,
        EngineEnum engine,
        TransmissionEnum transmission,
        String seller,
        boolean viewerIsOwner,
        String created
) {
    public String summary() {
        return brand + " " + model + ", " + year;
    }
}
