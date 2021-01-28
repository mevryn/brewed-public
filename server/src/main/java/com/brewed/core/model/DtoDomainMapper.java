package com.brewed.core.model;

import com.brewed.core.model.beercharatings.BeerRating;
import com.brewed.core.model.beercharatings.BeerRatingDto;

public interface DtoDomainMapper<Dto, Domain> {


    Domain toDomain(Dto dto);

    static BeerRating toDomain(BeerRatingDto dto) {
        return null;
    }

    Dto toDto(Domain beerCharacteristicsDto);
}
