package com.brewed.core.rest.brewery;


import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.beer.BeerNote;
import com.brewed.core.model.beer.BeerType;
import com.brewed.core.model.brewery.Brewery;

import java.util.ArrayList;
import java.util.List;

public class BreweryTestUtils {

    static Brewery generateRandomBrewery() {
        return new Brewery("SAMPLE_BREWERY_NAME");
    }

    static List<Brewery> generateRandomBreweries(int amount) {
        List<Brewery> breweries = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            breweries.add(generateRandomBrewery());
        }
        return breweries;
    }

    static Beer generateSampleBeer(Brewery brewery) {
        Beer beer = new Beer();
        beer.setName("BeerDto");
        beer.setBrewery(brewery);
        beer.setBeerType(new BeerType("Ale"));
        beer.setAlcoholPercentage(5D);
        beer.setPrice(5.00);
        beer.setBeerNote(new BeerNote());
        return beer;
    }
}
