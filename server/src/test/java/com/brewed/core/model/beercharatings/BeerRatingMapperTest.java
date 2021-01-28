package com.brewed.core.model.beercharatings;

import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.useridentifier.UserIdentifier;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class BeerRatingMapperTest {

    BeerRatingMapper beerRatingMapper = new BeerRatingMapper();

    @Test
    void shouldMapToDomain() {
        BeerRatingDto dto = new BeerRatingDto();
        UUID beerId = UUID.randomUUID();
        dto.setBeer(beerId);
        UUID ratingId = UUID.randomUUID();
        dto.setId(ratingId);
        double bitterness = 0.76;
        dto.setBitterness(bitterness);
        double sour = 0.66;
        dto.setSour(sour);
        double dryness = 0.46;
        dto.setDryness(dryness);
        double sweetness = 0.2;
        dto.setSweetness(sweetness);
        double score = 0.8;
        dto.setScore(score);
        String identifier = "Test";
        dto.setUserIdentifier(identifier);
        BeerRating beerRating = beerRatingMapper.toDomain(dto);
        assertAll(() -> assertThat(beerRating).isNotNull(),
                  () -> assertThat(beerRating.getId()).isEqualTo(ratingId),
                  () -> assertThat(beerRating.getBeer()).isEqualTo(new Beer(beerId)),
                  () -> assertThat(beerRating.getScore()).isEqualTo(score),
                  () -> assertThat(beerRating.getBitterness()).isEqualTo(bitterness),
                  () -> assertThat(beerRating.getDryness()).isEqualTo(dryness),
                  () -> assertThat(beerRating.getSour()).isEqualTo(sour),
                  () -> assertThat(beerRating.getUserIdentifier()).isEqualTo(new UserIdentifier(identifier)),
                  () -> assertThat(beerRating.getSweetness()).isEqualTo(sweetness)
        );
    }

    @Test
    void shouldMapToDto() {
        BeerRating beerRating = new BeerRating();
        UUID beerId = UUID.randomUUID();
        beerRating.setBeer(new Beer(beerId));
        UUID ratingId = UUID.randomUUID();
        beerRating.setId(ratingId);
        double bitterness = 0.76;
        beerRating.setBitterness(bitterness);
        double sour = 0.66;
        beerRating.setSour(sour);
        double dryness = 0.46;
        beerRating.setDryness(dryness);
        double sweetness = 0.2;
        beerRating.setSweetness(sweetness);
        double score = 0.8;
        beerRating.setScore(score);
        String identifier = "Test";
        beerRating.setUserIdentifier(new UserIdentifier(identifier));
        BeerRatingDto dto = beerRatingMapper.toDto(beerRating);
        assertAll(() -> assertThat(dto).isNotNull(),
                  () -> assertThat(dto.getId()).isEqualTo(ratingId),
                  () -> assertThat(dto.getBeer()).isEqualTo(beerId),
                  () -> assertThat(dto.getScore()).isEqualTo(score),
                  () -> assertThat(dto.getBitterness()).isEqualTo(bitterness),
                  () -> assertThat(dto.getDryness()).isEqualTo(dryness),
                  () -> assertThat(dto.getSour()).isEqualTo(sour),
                  () -> assertThat(dto.getUserIdentifier()).isEqualTo(identifier),
                  () -> assertThat(dto.getSweetness()).isEqualTo(sweetness)
        );
    }
}
