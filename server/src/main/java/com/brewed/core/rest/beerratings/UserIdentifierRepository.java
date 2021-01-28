package com.brewed.core.rest.beerratings;

import com.brewed.core.model.useridentifier.UserIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserIdentifierRepository extends JpaRepository<UserIdentifier, UUID>, BrewedBeerRatingsRepository {
    @Query(value = "SELECT userId FROM UserIdentifier userId where userId.identifier =:userId")
    Optional<UserIdentifier> getUserIdentifierByIdentifier(@Param(value = "userId") String userId);

    @Query(value = "SELECT userId FROM UserIdentifier userId where userId.identifier  like :userId")
    Collection<UserIdentifier> getUserIdentifierLikeIdentifier(@Param(value = "userId") String userId);
}
