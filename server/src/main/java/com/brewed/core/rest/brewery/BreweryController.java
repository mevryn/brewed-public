package com.brewed.core.rest.brewery;

import com.brewed.core.model.beercharatings.CompoundBeerRating;
import com.brewed.core.model.brewery.Brewery;
import com.brewed.core.rest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@CrossOrigin
@RestController
@RequestMapping("/api/brewery")
public class BreweryController {

    private final BreweryService breweryService;


    @Autowired
    public BreweryController(BreweryService breweryService) {
        this.breweryService = breweryService;
    }


    @GetMapping("/{id}")
    public Brewery getBrewery(@PathVariable("id") UUID id) {
        Optional<Brewery> breweryOptional = breweryService.getBrewery(id);
        return breweryOptional.orElseThrow(NotFoundException::new);
    }

    @GetMapping("/report")
    public List<CompoundBeerRating> getReport(@RequestParam("breweryId") String breweryId) {
        return this.breweryService.getRatingsForBrewery(breweryId);
    }


    @GetMapping
    public List<Brewery> getBreweries(@RequestParam(name = "name", required = false) String name) {
        if (isNull(name) || name.isEmpty()) {
            return breweryService.getAllBreweries();
        } else {
            return List.of(breweryService.getBrewery(name)
                                         .orElseThrow());
        }
    }

    @GetMapping("/filter")
    public List<Brewery> getFilteredBreweries(@RequestParam(name = "breweryName") String name) {
        return breweryService.getFilteredBreweries(name);
    }


    @PostMapping
    public ResponseEntity<Brewery> addBrewery(@RequestBody Brewery brewery) {
        return new ResponseEntity<>(breweryService.addBrewery(brewery), HttpStatus.CREATED);
    }

    @PostMapping("/add/all")
    public List<Brewery> addProvidedBreweries(@RequestBody Collection<Brewery> breweries) {
        return breweryService.addProvidedBreweries(breweries);
    }

    @PutMapping
    public void updateBrewery(@RequestBody Brewery brewery) {
        breweryService.update(brewery);
    }

    @DeleteMapping("/{id}")
    public void deleteBrewery(@PathVariable("id") UUID id) {
        breweryService.delete(id);
    }

    @PostMapping("/deleteGiven")
    public void deleteAllBeers(@RequestBody Collection<Brewery> breweries) {
        breweryService.deleteAll(breweries);
    }

    @DeleteMapping("/delete/all")
    public void deleteAllBreweries() {
        breweryService.deleteAll();
    }
}
