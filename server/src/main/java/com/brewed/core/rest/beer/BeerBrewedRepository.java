package com.brewed.core.rest.beer;

import com.brewed.api.FilterDescription;
import com.brewed.core.model.beer.Beer;

import java.util.List;

public interface BeerBrewedRepository {
    List<Beer> findAllBeersThatMatchEnvelopeContent(BeerRequestEnvelope envelope);

    List<Beer> filter(FilterDescription description);
}
