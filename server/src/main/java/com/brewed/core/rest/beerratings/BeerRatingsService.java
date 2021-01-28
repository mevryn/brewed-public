package com.brewed.core.rest.beerratings;


import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.beercharatings.*;
import com.brewed.core.rest.beer.BeerRepository;
import com.brewed.core.rest.exception.NotFoundException;
import com.brewed.core.utils.BeerRatingComputer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BeerRatingsService {

    private final BeerRatingMapper beerRatingMapper = new BeerRatingMapper();
    @Autowired
    private BeerRepository beerRepository;
    @Autowired
    private BeerRatingsRepository repository;

    @Autowired
    private UserIdentifierRepository userIdentifierRepository;

    public BeerRatingDto getBeerRatingsForUser(UUID beerId, String userId) {
        Optional<BeerRating> beerRatingForUser = repository.getBeerRatingForUser(beerId, userId);
        return beerRatingMapper.toDto(beerRatingForUser.orElseThrow(NotFoundException::new));
    }

    public Optional<BeerRating> getCharacteristicsById(UUID id) {
        return repository.findById(id);
    }

    public BeerRating postRating(BeerRatingDto ratingDto) {

        Optional<BeerRating> beerRatingForUser = repository.getBeerRatingForUser(ratingDto.getBeer(),
                                                                                 ratingDto.getUserIdentifier());
        if (beerRatingForUser
                .isEmpty()) {
            Beer beer = beerRepository.findById(ratingDto.getBeer())
                                      .orElseThrow(NotFoundException::new);
            BeerRating beerRating = beerRatingMapper.toDomain(ratingDto);
            beerRating.setBeer(beer);
            userIdentifierRepository.getUserIdentifierByIdentifier(beerRating.getUserIdentifier()
                                                                             .getIdentifier())
                                    .ifPresentOrElse(beerRating::setUserIdentifier,
                                                     () -> userIdentifierRepository.save(
                                                             beerRating.getUserIdentifier()));
            return repository.save(beerRating);
        } else {
            BeerRating result = beerRatingMapper.toDomain(ratingDto);
            BeerRating domain = beerRatingForUser.get();
            result.setId(domain
                                 .getId());
            result.setUserIdentifier(domain
                                             .getUserIdentifier());
            result.setBeer(domain
                                   .getBeer());
            return repository.save(result);
        }
    }


    public void delete(UUID beerId, String userId) {
        Optional<BeerRating> beerRatingForUser = repository.getBeerRatingForUser(beerId, userId);
        if (beerRatingForUser.isEmpty()) {
            throw new NotFoundException();
        }
        repository.delete(beerRatingForUser.get());
    }

    public List<BeerRatingDto> getAllRatingsForUser(String userId) {
        return repository.getAllUsersRatings(userId)
                         .stream()
                         .map(beerRatingMapper::toDto)
                         .collect(Collectors.toList());
    }

    public CompoundBeerRatingDto getMeanRatingOfBeer(UUID id) {
        List<BeerRating> allBeerRatings = repository.getAllBeerRatings(id);
        BeerRatingComputer beerRatingComputer = new BeerRatingComputer();
        return beerRatingComputer.computeCompoundBeerRating(allBeerRatings);
    }

    public CompoundBeerRating computeReportForBeer(Collection<BeerRating> beerRatings) {
        BeerRatingComputer beerRatingComputer = new BeerRatingComputer();
        return beerRatingComputer.computeBeerReport(beerRatings);
    }


    public Collection<BeerRating> getAllBeerRatingOfBeer(Beer beer) {
        return repository.getAllBeerRatings(beer.getId());
    }

    public Collection<BeerRatingListDto> getFilteredRatings(String beerName, String userIdentifier) {
        Collection<BeerRating> filteredRatings = repository.getFilteredRatings(beerName, userIdentifier);
        return filteredRatings.stream()
                              .map(BeerRatingMapper::toListDto)
                              .collect(Collectors.toList());
    }
}
