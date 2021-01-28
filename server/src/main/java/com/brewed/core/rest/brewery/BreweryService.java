package com.brewed.core.rest.brewery;

import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.beercharatings.BeerRating;
import com.brewed.core.model.beercharatings.CompoundBeerRating;
import com.brewed.core.model.brewery.Brewery;
import com.brewed.core.model.brewery.BreweryConstants;
import com.brewed.core.rest.beer.BeerRepository;
import com.brewed.core.rest.beer.BeerService;
import com.brewed.core.rest.beerratings.BeerRatingsService;
import com.brewed.core.rest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class BreweryService {

    private final BreweryRepository breweryRepository;
    private final BeerRepository beerRepository;
    private final BeerRatingsService beerRatingsService;

    @Autowired
    public BreweryService(BreweryRepository breweryRepository, BeerRepository beerRepository, BeerService beerService, BeerRatingsService beerRatingsService) {
        this.breweryRepository = breweryRepository;
        this.beerRepository = beerRepository;
        this.beerRatingsService = beerRatingsService;
    }

    Optional<Brewery> getBrewery(UUID id) {
        return breweryRepository.findById(id);
    }

    public Brewery addBrewery(Brewery brewery) {
        if (brewery.equals(BreweryConstants.DEFAULT_BREWERY)) {
            throw new IllegalArgumentException("Default value can't be modified");
        }
        return breweryRepository.saveAndFlush(brewery);
    }

    List<Brewery> getAllBreweries() {
        return breweryRepository.findAll();
    }

    List<Brewery> addProvidedBreweries(Collection<Brewery> breweries) {
        return breweryRepository.saveAll(breweries);
    }

    void delete(UUID id) {
        if (id.equals(BreweryConstants.DEFAULT_BREWERY.getId())) {
            throw new IllegalArgumentException("Default value can't be modified");
        }
        Collection<Beer> beerByBrewery = beerRepository.findBeersByBreweryId(id);
        for (Beer beer : beerByBrewery) {
            beer.setBrewery(BreweryConstants.DEFAULT_BREWERY);
        }
        beerRepository.saveAll(beerByBrewery);
        breweryRepository.deleteById(id);
    }

    void update(Brewery brewery) {
        if (breweryRepository.existsById(brewery.getId())) {
            breweryRepository.save(brewery);
        } else {
            throw new NotFoundException();
        }
    }

    Optional<Brewery> getBrewery(String name) {
        return breweryRepository.findByName(name);
    }

    void deleteAll() {
        List<Brewery> all = breweryRepository.findAll();
        List<Brewery> withoutDefault = all.stream()
                                          .filter(brewery -> !brewery.equals(BreweryConstants.DEFAULT_BREWERY))
                                          .collect(Collectors.toList());
        breweryRepository.deleteAll(withoutDefault);
    }

    void deleteAll(Collection<Brewery> breweries) {
        if (breweries.contains(BreweryConstants.DEFAULT_BREWERY)) {
            throw new IllegalArgumentException("Default value can't be modified");
        }
        Collection<Beer> beerByBrewery = beerRepository.findBeerByBrewery(breweries);
        for (Beer beer : beerByBrewery) {
            beer.setBrewery(BreweryConstants.DEFAULT_BREWERY);
        }
        beerRepository.saveAll(beerByBrewery);
        breweryRepository.deleteAll(breweries);
    }

    public List<CompoundBeerRating> getRatingsForBrewery(String breweryId) {
        Optional<Brewery> optionalBrewery = breweryRepository.findById(UUID.fromString(breweryId));
        ArrayList<CompoundBeerRating> beerRatings = new ArrayList<>();
        Set<Beer> craftedBeers = optionalBrewery.orElseThrow(NotFoundException::new)
                                                .getCraftedBeers();
        for (Beer craftedBeer : craftedBeers) {
            Collection<BeerRating> allBeerRatingOfBeer = beerRatingsService.getAllBeerRatingOfBeer(craftedBeer);
            if (!allBeerRatingOfBeer.isEmpty()) {
                beerRatings.add(beerRatingsService.computeReportForBeer(allBeerRatingOfBeer));
            }
        }
        return beerRatings;
    }

    public List<Brewery> getFilteredBreweries(String name) {
        return breweryRepository.findFilteredBreweries(name.toLowerCase());
    }
}
