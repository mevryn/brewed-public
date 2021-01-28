package com.brewed.core.utils;

import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.beercharatings.BeerRating;
import com.brewed.core.model.beercharatings.CompoundBeerRating;
import com.brewed.core.model.beercharatings.CompoundBeerRatingDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class BeerRatingComputer {
    public CompoundBeerRating computeBeerReport(Collection<BeerRating> beerRatings) {
        CompoundBeerRating result = new CompoundBeerRating();
        result.setBeer(getBeer(beerRatings));
        calculateReport(beerRatings, result);
        return result;
    }

    private void calculateReport(Collection<BeerRating> beerRatings, CompoundBeerRating result) {
        result.setScore(calculateAverageScore(beerRatings.stream()
                                                         .map(BeerRating::getScore)
                                                         .collect(Collectors.toList())));
        result.setBitterness(calculateAttributesMeanForReport(beerRatings.stream()
                                                                         .map(BeerRating::getBitterness)
                                                                         .collect(Collectors.toList())));

        result.setDryness((calculateAttributesMeanForReport(beerRatings.stream()
                                                                       .map(BeerRating::getDryness)
                                                                       .collect(Collectors.toList()))));

        result.setSour((calculateAttributesMeanForReport(beerRatings.stream()
                                                                    .map(BeerRating::getSour)
                                                                    .collect(Collectors.toList()))));

        result.setSweetness((calculateAttributesMeanForReport(beerRatings.stream()
                                                                         .map(BeerRating::getSweetness)
                                                                         .collect(Collectors.toList()))));
    }

    private void calculateScores(List<BeerRating> beerRatings, CompoundBeerRatingDto result) {
        result.setScore(calculateAverageScore(beerRatings.stream()
                                                         .map(BeerRating::getScore)
                                                         .collect(Collectors.toList())));
        result.setBitterness(calculateAttributesMean(beerRatings.stream()
                                                                .map(BeerRating::getBitterness)
                                                                .collect(Collectors.toList())));

        result.setDryness((calculateAttributesMean(beerRatings.stream()
                                                              .map(BeerRating::getDryness)
                                                              .collect(Collectors.toList()))));

        result.setSour((calculateAttributesMean(beerRatings.stream()
                                                           .map(BeerRating::getSour)
                                                           .collect(Collectors.toList()))));

        result.setSweetness((calculateAttributesMean(beerRatings.stream()
                                                                .map(BeerRating::getSweetness)
                                                                .collect(Collectors.toList()))));
    }

    public CompoundBeerRatingDto computeCompoundBeerRating(List<BeerRating> beerRatings) {
        CompoundBeerRatingDto result = new CompoundBeerRatingDto();
        result.setBeerId(getBeerIdentifier(beerRatings));
        calculateScores(beerRatings, result);
        return result;
    }


    private Beer getBeer(Collection<BeerRating> beerRatings) {
        int size = beerRatings.stream()
                              .map(BeerRating::getBeer)
                              .collect(toSet())
                              .size();
        if (size == 1) {
            return beerRatings.stream()
                              .findAny()
                              .orElseThrow()
                              .getBeer();
        } else {
            throw new IllegalArgumentException(
                    size > 1 ? "Programmer error:Not unique ids" : "Programmer error:Not beer found");
        }
    }

    private UUID getBeerIdentifier(List<BeerRating> beerRatings) {
        if (beerRatings.stream()
                       .map(BeerRating::getBeer)
                       .map(Beer::getId)
                       .collect(toSet())
                       .size() == 1) {
            return beerRatings.get(0)
                              .getBeer()
                              .getId();
        } else {
            throw new IllegalArgumentException("Programmer error:Not unique ids");
        }
    }

    private Double calculateAverageScore(List<Double> scores) {
        Double averageScore = 0.0;
        if (scores.isEmpty()) {
            return averageScore;
        } else {
            for (Double score : scores) {
                averageScore += score;
            }
            double result = averageScore / scores.size();
            BigDecimal resultRounded = new BigDecimal(Double.toString(result));
            resultRounded = resultRounded.setScale(2, RoundingMode.HALF_UP);
            return resultRounded.doubleValue();
        }
    }

    private Double calculateAttributesMean(List<Double> attributesScores) {
        if (attributesScores.size() < 5) {
            return null;
        } else {
            return calculateMean(attributesScores);
        }
    }

    private Double calculateMean(List<Double> attributesScores) {
        List<Double> filteredNulls = attributesScores.stream()
                                                     .filter(Objects::nonNull)
                                                     .sorted(Double::compareTo)
                                                     .collect(Collectors.toList());
        int size = filteredNulls.size();
        if (isEven(size)) {
            double result = (filteredNulls.get(size / 2) + filteredNulls.get((size / 2) - 1)) / 2;
            BigDecimal resultRounded = new BigDecimal(
                    Double.toString(result));
            resultRounded = resultRounded.setScale(2, RoundingMode.HALF_UP);
            return resultRounded.doubleValue();
        } else {
            return filteredNulls.get(size / 2);
        }
    }

    private Double calculateAttributesMeanForReport(List<Double> attributesScores) {
        return calculateMean(attributesScores);
    }

    private boolean isEven(int size) {
        return size % 2 == 0;
    }
}
