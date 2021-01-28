package com.brewed.core.rest.beer;

import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.beer.BeerType;
import com.brewed.core.model.brewery.Brewery;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BeerTestUtils {

    static List<Beer> generateRandomBeers(int amount) {
        List<Beer> beers = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Beer beer = new Beer();
            beer.setId(UUID.randomUUID());
            beers.add(beer);
        }
        return beers;
    }

    static Beer generateSampleBeer() {
        Beer beer = new Beer();
        beer.setName("BeerDto");
        beer.setId(UUID.randomUUID());
        Brewery brewery = new Brewery();
        brewery.setAddress("Rostokowa 22");
        brewery.setDescription("Produce very good beer");
        brewery.setId(UUID.randomUUID());
        brewery.setName("VeryGoodBeerCompany");
        beer.setBrewery(brewery);
        beer.setBeerType(new BeerType("Ale"));
        beer.setAlcoholPercentage(5D);
        beer.setPrice(5.00);
        return beer;
    }
}
