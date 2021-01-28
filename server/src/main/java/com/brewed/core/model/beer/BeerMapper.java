package com.brewed.core.model.beer;

import com.brewed.core.model.DtoDomainMapper;
import com.brewed.core.model.brewery.Brewery;

import static java.util.Objects.nonNull;

public class BeerMapper implements DtoDomainMapper<BeerDto, Beer> {


    @Override
    public Beer toDomain(BeerDto beerDto) {
        return Beer.builder()
                   .id(beerDto.getId())
                   .barCode(beerDto.getBarCode())
                   .alcoholPercentage(beerDto.getAlcoholPercentage())
                   .beerType(new BeerType(beerDto.getBeerTypeId()))
                   .brewery(new Brewery(beerDto.getBreweryId()))
                   .name(
                           beerDto.getName())
                   .beerNote(new BeerNote(beerDto.getBeerNoteId()))
                   .price(beerDto.getPrice())
                   .servingTemperature(beerDto.getServingTemperature())
                   .ibu(beerDto.getIbu())
                   .build();
    }

    @Override
    public BeerDto toDto(Beer beer) {
        return BeerDto.builder()
                      .alcoholPercentage(beer.getAlcoholPercentage())
                      .barCode(
                              beer.getBarCode())
                      .beerTypeId(nonNull(beer.getBeerType()) ? beer.getBeerType()
                                                                    .getId() : null)
                      .breweryId(nonNull(beer.getBrewery()) ?
                                         beer.getBrewery()
                                             .getId() : null)
                      .id(beer.getId())
                      .name(beer.getName())
                      .beerNoteId(nonNull(beer.getBeerNote()) ? beer.getBeerNote()
                                                                    .getId() : null)
                      .servingTemperature(beer.getServingTemperature())
                      .ibu(beer.getIbu())
                      .price(
                              beer.getPrice())
                      .build();
    }
}
