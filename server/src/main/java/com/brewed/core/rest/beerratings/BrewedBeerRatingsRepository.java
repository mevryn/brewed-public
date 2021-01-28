package com.brewed.core.rest.beerratings;

import com.brewed.core.model.beercharatings.BeerRating;

import java.util.Collection;

public interface BrewedBeerRatingsRepository {

    Collection<BeerRating> getFilteredRatings(String beerName, String userIdentifier);
}
