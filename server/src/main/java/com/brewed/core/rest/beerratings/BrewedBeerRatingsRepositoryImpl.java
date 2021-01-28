package com.brewed.core.rest.beerratings;

import com.brewed.core.model.beercharatings.BeerRating;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.nonNull;


@Repository
public class BrewedBeerRatingsRepositoryImpl implements BrewedBeerRatingsRepository {

    private final EntityManager entityManager;

    public BrewedBeerRatingsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Collection<BeerRating> getFilteredRatings(String beerName, String userIdentifier) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BeerRating> criteriaQuery = criteriaBuilder.createQuery(BeerRating.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<BeerRating> ratingDtoRoot = criteriaQuery.from(BeerRating.class);
        if (nonNull(beerName) && !beerName.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(ratingDtoRoot.get("beer")
                                                                                   .get("name")),
                                                "%" + beerName
                                                        .toLowerCase() + "%"));
        }
        if (nonNull(userIdentifier) && !userIdentifier.isEmpty()) {

            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(ratingDtoRoot.get("userIdentifier")
                                                                                   .get("identifier")),
                                                "%" + userIdentifier
                                                        .toLowerCase() + "%"));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery)
                            .getResultList();
    }
}
