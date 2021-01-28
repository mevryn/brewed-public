package com.brewed.core.rest.beerratings;

import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.beercharatings.BeerRating;
import com.brewed.core.model.beercharatings.BeerRatingDto;
import com.brewed.core.model.beercharatings.BeerRatingMapper;
import com.brewed.core.model.useridentifier.UserIdentifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BeerRatingsServiceTest {

    @InjectMocks
    private BeerRatingsService service;
    @Mock
    private BeerRatingsRepository repository;


    @Test
    void shouldUserOnlyCreateRatingIfItNotExistsAlready() {
        BeerRatingMapper beerRatingMapper = new BeerRatingMapper();
        BeerRatingDto ratingDto = new BeerRatingDto();
        UUID beerId = UUID.randomUUID();
        String userId = "42342315123523";
        ratingDto.setBeer(beerId);
        ratingDto.setUserIdentifier(userId);
        Optional<BeerRating> optionalDomainRatingObject = Optional.of(beerRatingMapper.toDomain(ratingDto));
        when(repository.getBeerRatingForUser(beerId, userId)).thenReturn(
                optionalDomainRatingObject);
        service.postRating(ratingDto);
        verify(repository).save(optionalDomainRatingObject.get());
    }

    @Test
    void shouldGetBeerRatingsIfUserRequestHisRatings() {
        Beer beer = new Beer();
        UUID beerId = UUID.randomUUID();
        beer.setId(beerId);
        String userId = "45235523452";
        BeerRating beerRating = new BeerRating();
        beerRating.setBeer(beer);
        beerRating.setUserIdentifier(new UserIdentifier(userId));

        when(repository.getBeerRatingForUser(beerId, userId)).thenReturn(Optional.of(beerRating));
        service.getBeerRatingsForUser(beerId, userId);
        verify(repository).getBeerRatingForUser(beerId, userId);
    }
}
