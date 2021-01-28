package com.brewed.core.model.beer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerDto {

    private UUID id;
    private String name;
    private UUID breweryId;
    private String barCode;
    private Double price;
    private Double alcoholPercentage;
    private UUID beerTypeId;
    private UUID beerNoteId;
    private Double servingTemperature;
    private Integer ibu;
}
