package com.brewed.core.rest.beertype;

import com.brewed.core.model.beer.BeerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BeerTypeRepository extends JpaRepository<BeerType, UUID> {
    @Query(value = "SELECT beerType FROM BeerType beerType where beerType.type = :type")
    Optional<BeerType> findByName(@Param(value = "type") String type);

}
