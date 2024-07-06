package org.softuni.mobilelele.model.dto;

import jakarta.validation.constraints.*;
import org.softuni.mobilelele.model.enums.EngineEnum;
import org.softuni.mobilelele.model.enums.TransmissionEnum;
import org.softuni.mobilelele.model.validation.YearNotInTheFuture;

import java.math.BigDecimal;

public record CreateOfferDTO(

        @NotEmpty
        @Size(min = 5, max = 512)
        String description,

        @NotNull
        @Positive
        Long modelId,

        @NotNull
        EngineEnum engine,

        @NotNull
        TransmissionEnum transmission,

        @NotEmpty
        String imageUrl,

        @YearNotInTheFuture
        @NotNull
        @Min(1930)
        Integer year,

        @NotNull
        @Positive
        Long mileage,

        @NotNull
        @Positive
        BigDecimal price)
{
        public static CreateOfferDTO empty() {
               return new CreateOfferDTO(null, null, null, null, null, null, null, null);
        }
}
