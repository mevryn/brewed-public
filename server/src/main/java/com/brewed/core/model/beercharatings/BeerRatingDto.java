package com.brewed.core.model.beercharatings;

import java.util.Objects;
import java.util.UUID;

public class BeerRatingDto {


    private UUID id;
    private String userIdentifier;
    private Double score;
    private Double sweetness;
    private Double bitterness;
    private Double dryness;
    private Double sour;
    private UUID beer;

    public BeerRatingDto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeerRatingDto that = (BeerRatingDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userIdentifier, that.userIdentifier) &&
                Objects.equals(score, that.score) &&
                Objects.equals(sweetness, that.sweetness) &&
                Objects.equals(bitterness, that.bitterness) &&
                Objects.equals(dryness, that.dryness) &&
                Objects.equals(sour, that.sour) &&
                Objects.equals(beer, that.beer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userIdentifier, score, sweetness, bitterness, dryness, sour, beer);
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public UUID getBeer() {
        return beer;
    }

    public void setBeer(UUID beer) {
        this.beer = beer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getSweetness() {
        return sweetness;
    }

    public void setSweetness(Double sweetness) {
        this.sweetness = sweetness;
    }

    public Double getBitterness() {
        return bitterness;
    }

    public void setBitterness(Double bitterness) {
        this.bitterness = bitterness;
    }

    public Double getDryness() {
        return dryness;
    }

    public void setDryness(Double dryness) {
        this.dryness = dryness;
    }

    public Double getSour() {
        return sour;
    }

    public void setSour(Double sour) {
        this.sour = sour;
    }


}
