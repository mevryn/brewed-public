package com.brewed.core.rest.beerratings;

import com.brewed.core.model.beercharatings.BeerRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerRatingsRepository extends JpaRepository<BeerRating, UUID>, BrewedBeerRatingsRepository {

    @Query(value = "SELECT ratings FROM BeerRating ratings where ratings.userIdentifier.identifier =:userId and ratings.beer" +
            ".id=:beerId")
    Optional<BeerRating> getBeerRatingForUser(@Param(value = "beerId") UUID beerId,
                                              @Param(value = "userId") String userId);

    @Query(value = "SELECT ratings FROM BeerRating ratings where ratings.userIdentifier.identifier =:userId")
    List<BeerRating> getAllUsersRatings(@Param(value = "userId") String userId);

    @Query(value = "SELECT ratings FROM BeerRating ratings where ratings.beer.id =:beerId")
    List<BeerRating> getAllBeerRatings(@Param(value = "beerId") UUID beerId);


}
