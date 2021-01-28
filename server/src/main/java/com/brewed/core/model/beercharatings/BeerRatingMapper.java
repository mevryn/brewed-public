package com.brewed.core.model.beercharatings;

import com.brewed.core.model.DtoDomainMapper;
import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.useridentifier.UserIdentifier;
import org.springframework.stereotype.Component;

@Component
public class BeerRatingMapper implements DtoDomainMapper<BeerRatingDto, BeerRating> {

    public static BeerRatingListDto toListDto(BeerRating domain) {
        BeerRatingListDto dto = new BeerRatingListDto();
        dto.setBeerId(domain.getBeer()
                            .getId()
                            .toString());
        dto.setBeerName(domain.getBeer()
                              .getName());
        dto.setUserIdentifier(domain.getUserIdentifier()
                                    .getIdentifier());
        return dto;
    }

    @Override
    public BeerRatingDto toDto(BeerRating domain) {
        BeerRatingDto dto = new BeerRatingDto();
        dto.setId(domain.getId());
        dto.setBeer(domain.getBeer()
                          .getId());
        dto.setBitterness(domain.getBitterness());
        dto.setSour(domain.getSour());
        dto.setSweetness(domain.getSweetness());
        dto.setDryness(domain.getDryness());
        dto.setScore(domain.getScore());
        dto.setUserIdentifier(domain.getUserIdentifier()
                                    .getIdentifier());
        return dto;
    }

    @Override
    public BeerRating toDomain(BeerRatingDto dto) {
        BeerRating beerRating = new BeerRating();
        beerRating.setId(dto.getId());
        Beer beer = new Beer();
        beer.setId(dto.getBeer());
        beerRating.setBeer(beer);
        beerRating.setBitterness(dto.getBitterness());
        beerRating.setSour(dto.getSour());
        beerRating.setSweetness(dto.getSweetness());
        beerRating.setDryness(dto.getDryness());
        beerRating.setScore(dto.getScore());
        beerRating.setUserIdentifier(new UserIdentifier(dto.getUserIdentifier()));
        return beerRating;
    }
}
