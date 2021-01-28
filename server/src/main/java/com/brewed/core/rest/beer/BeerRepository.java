package com.brewed.core.rest.beer;

import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.beer.BeerNote;
import com.brewed.core.model.beer.BeerType;
import com.brewed.core.model.brewery.Brewery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface BeerRepository extends JpaRepository<Beer, UUID>, BeerBrewedRepository {
    @Query(value = "SELECT beer FROM Beer beer where beer.barCode = :barCode")
    List<Beer> findBeerByBarCode(@Param(value = "barCode") String barCode);

    @Query(value = "SELECT beer FROM Beer beer where beer.name like :name")
    Collection<Beer> findBeerByName(@Param(value = "name") String name);

    @Query(value = "SELECT beer FROM Beer beer where beer.brewery in :breweries")
    Collection<Beer> findBeerByBrewery(@Param(value = "breweries") Collection<Brewery> breweries);

    @Query(value = "SELECT beer FROM Beer beer where beer.beerType.id = :beerTypeId")
    Collection<Beer> findBeersByBeerTypeId(@Param(value = "beerTypeId") UUID beerTypeId);

    @Query(value = "SELECT beer FROM Beer beer where beer.beerNote in :beerNotes")
    Collection<Beer> findBeerByBeerNote(@Param(value = "beerNotes") Collection<BeerNote> beerNotes);

    @Query(value = "SELECT beer FROM Beer beer where beer.beerNote.id = :beerNoteId")
    Collection<Beer> findBeersByBeerNoteId(@Param(value = "beerNoteId") UUID beerNoteId);

    @Query(value = "SELECT beer FROM Beer beer where beer.beerType in :beerTypes")
    Collection<Beer> findBeerByBeerType(@Param(value = "beerTypes") Collection<BeerType> beerTypes);

    @Query(value = "SELECT beer FROM Beer beer where beer.brewery.id = :breweryId")
    Collection<Beer> findBeersByBreweryId(@Param(value = "breweryId") UUID breweryId);


}
