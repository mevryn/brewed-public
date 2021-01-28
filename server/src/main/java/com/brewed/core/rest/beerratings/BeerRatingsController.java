package com.brewed.core.rest.beerratings;


import com.brewed.core.model.beercharatings.BeerRating;
import com.brewed.core.model.beercharatings.BeerRatingDto;
import com.brewed.core.model.beercharatings.BeerRatingListDto;
import com.brewed.core.model.beercharatings.CompoundBeerRatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/beer-ratings")
public class BeerRatingsController {

    @Autowired
    private BeerRatingsService service;

    @GetMapping
    public BeerRatingDto getBeerRatingForUser(@RequestParam(name = "beerId") UUID beerId,
                                              @RequestParam(name = "userId") String userId) {
        return service.getBeerRatingsForUser(beerId, userId);
    }

    @GetMapping("/{id}")
    public Optional<BeerRating> getBeerRatingById(@PathVariable(name = "id") String uuid) {
        return service.getCharacteristicsById(UUID.fromString(uuid));
    }

    @GetMapping("/filter")
    public Collection<BeerRatingListDto> getFilteredRatings(@RequestParam(name = "beerName", required = false) String beerName,
                                                            @RequestParam(name = "userIdentifier", required = false) String userIdentifier) {
        return service.getFilteredRatings(beerName, userIdentifier);
    }

    @PutMapping
    public BeerRating postRating(@RequestBody BeerRatingDto dto) {
        return service.postRating(dto);
    }

    @DeleteMapping
    public void delete(@RequestParam(name = "beerId") UUID beerId, @RequestParam(name = "userId") String userId) {
        service.delete(beerId, userId);
    }

    @GetMapping("/all")
    public List<BeerRatingDto> getAllUserRatings(@RequestParam(name = "userId") String userId) {
        return service.getAllRatingsForUser(userId);
    }

    @GetMapping("/compound")
    public CompoundBeerRatingDto getMedianRatingOfBeer(@RequestParam(name = "beerId") UUID beerId) {
        return service.getMeanRatingOfBeer(beerId);
    }
}
