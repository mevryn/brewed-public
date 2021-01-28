package com.brewed.core.rest.beer;

import com.brewed.api.FilterDescription;
import com.brewed.core.model.beer.Beer;
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
public class BeerBrewedRepositoryImpl implements BeerBrewedRepository {
    final EntityManager entityManager;

    public BeerBrewedRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Beer> findAllBeersThatMatchEnvelopeContent(BeerRequestEnvelope envelope) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Beer> criteriaQuery = criteriaBuilder.createQuery(Beer.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Beer> beerRoot = criteriaQuery.from(Beer.class);
        if (nonNull(envelope.getBarCode()) && !envelope.getBarCode()
                                                       .isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(beerRoot.get("barCode")),
                                                "%" + envelope.getBarCode()
                                                              .toLowerCase() + "%"));
        }
        if (nonNull(envelope.getName()) && !envelope.getName()
                                                    .equals("")) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(beerRoot.get("name")),
                                                "%" + envelope.getName()
                                                              .toLowerCase() + "%"));
        }
        if (nonNull(envelope.getBreweryName()) && !envelope.getBreweryName()
                                                           .equals("")) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(beerRoot.get("brewery")
                                                                              .get("name")),
                                                "%" + envelope.getBreweryName()
                                                              .toLowerCase() + "%"));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery)
                            .getResultList();
    }

    @Override
    public List<Beer> filter(FilterDescription description) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Beer> criteriaQuery = criteriaBuilder.createQuery(Beer.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Beer> beerRoot = criteriaQuery.from(Beer.class);
        predicates.addAll(prepareAlcoholPercentageCriteria(description, criteriaBuilder, beerRoot));
        predicates.addAll(prepareIbuCriteria(description, criteriaBuilder, beerRoot));
        predicates.addAll(prepareSweetnessCriteria(description, criteriaBuilder, beerRoot));
        predicates.addAll(prepareBitternessCriteria(description, criteriaBuilder, beerRoot));
        predicates.addAll(prepareSournessCriteria(description, criteriaBuilder, beerRoot));
        if (nonNull(description.getBeerTypes()) && !description.getBeerTypes()
                                                               .isEmpty()) {
            predicates.add(beerRoot.get("beerType")
                                   .get("type")
                                   .in(description.getBeerTypes()));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery)
                            .getResultList();
    }


    private Collection<? extends Predicate> prepareSournessCriteria(FilterDescription description, CriteriaBuilder criteriaBuilder, Root<Beer> beerRoot) {
        List<Predicate> sournessCriteria = new ArrayList<>();
        if (nonNull(description.getSourness())) {
            if (nonNull(description.getSourness()
                                   .getMinimum())) {
                sournessCriteria.add(criteriaBuilder.greaterThanOrEqualTo(beerRoot.get("sourness"),
                                                                          description.getSourness()
                                                                                     .getMinimum()));
            }
            if (nonNull(description.getSourness()
                                   .getMaximum())) {
                sournessCriteria.add(criteriaBuilder.lessThanOrEqualTo(beerRoot.get("sourness"),
                                                                       description.getSourness()
                                                                                  .getMaximum()));
            }
        }
        return sournessCriteria;
    }

    private Collection<? extends Predicate> prepareBitternessCriteria(FilterDescription description, CriteriaBuilder criteriaBuilder, Root<Beer> beerRoot) {
        List<Predicate> bitternessCriteria = new ArrayList<>();
        if (nonNull(description.getBitterness())) {
            if (nonNull(description.getBitterness()
                                   .getMinimum())) {
                bitternessCriteria.add(criteriaBuilder.greaterThanOrEqualTo(beerRoot.get("bitterness"),
                                                                            description.getBitterness()
                                                                                       .getMinimum()));
            }
            if (nonNull(description.getBitterness()
                                   .getMaximum())) {
                bitternessCriteria.add(criteriaBuilder.lessThanOrEqualTo(beerRoot.get("bitterness"),
                                                                         description.getBitterness()
                                                                                    .getMaximum()));
            }
        }
        return bitternessCriteria;
    }

    private Collection<? extends Predicate> prepareSweetnessCriteria(FilterDescription description, CriteriaBuilder criteriaBuilder, Root<Beer> beerRoot) {
        List<Predicate> sweetnessPredicate = new ArrayList<>();
        if (nonNull(description.getSweetness())) {
            if (nonNull(description.getSweetness()
                                   .getMinimum())) {
                sweetnessPredicate.add(criteriaBuilder.greaterThanOrEqualTo(beerRoot.get("sweetness"),
                                                                            description.getSweetness()
                                                                                       .getMinimum()));
            }
            if (nonNull(description.getSweetness()
                                   .getMaximum())) {
                sweetnessPredicate.add(criteriaBuilder.lessThanOrEqualTo(beerRoot.get("sweetness"),
                                                                         description.getSweetness()
                                                                                    .getMaximum()));
            }
        }
        return sweetnessPredicate;
    }

    private Collection<? extends Predicate> prepareIbuCriteria(FilterDescription description, CriteriaBuilder criteriaBuilder, Root<Beer> beerRoot) {
        List<Predicate> ibuPredicates = new ArrayList<>();
        if (nonNull(description.getIbu())) {
            if (nonNull(description.getIbu()
                                   .getMinimum())) {
                ibuPredicates.add(criteriaBuilder.greaterThanOrEqualTo(beerRoot.get("ibu"),
                                                                       description.getIbu()
                                                                                  .getMinimum()));
            }
            if (nonNull(description.getIbu()
                                   .getMaximum())) {
                ibuPredicates.add(criteriaBuilder.lessThanOrEqualTo(beerRoot.get("ibu"),
                                                                    description.getIbu()
                                                                               .getMaximum()));
            }
        }
        return ibuPredicates;
    }

    private List<Predicate> prepareAlcoholPercentageCriteria(FilterDescription description, CriteriaBuilder criteriaBuilder, Root<Beer> beerRoot) {
        List<Predicate> alcoholPercentagePredicates = new ArrayList<>();
        if (nonNull(description.getAlcoholPercentageRange())) {
            if (nonNull(description.getAlcoholPercentageRange()
                                   .getMinimum())) {
                alcoholPercentagePredicates.add(criteriaBuilder.greaterThanOrEqualTo(beerRoot.get("alcoholPercentage"),
                                                                                     description.getAlcoholPercentageRange()
                                                                                                .getMinimum()));
            }
            if (nonNull(description.getAlcoholPercentageRange()
                                   .getMaximum())) {
                alcoholPercentagePredicates.add(criteriaBuilder.lessThanOrEqualTo(beerRoot.get("alcoholPercentage"),
                                                                                  description.getAlcoholPercentageRange()
                                                                                             .getMaximum()));
            }
        }
        return alcoholPercentagePredicates;
    }
}
