package com.brewed.core.rest.brewery;

import com.brewed.core.model.brewery.Brewery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BreweryRepository extends JpaRepository<Brewery, UUID> {
    @Query(value = "SELECT brewery FROM Brewery brewery where brewery.name = :name")
    Optional<Brewery> findByName(@Param(value = "name") String name);

    @Query(value = "SELECT brewery FROM Brewery brewery where lower( brewery.name) like %:breweryName%")
    List<Brewery> findFilteredBreweries(@Param(value = "breweryName") String breweryName);
}
