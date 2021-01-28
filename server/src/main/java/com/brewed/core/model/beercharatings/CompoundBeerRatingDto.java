package com.brewed.core.model.beercharatings;

import java.util.Objects;
import java.util.UUID;

public class CompoundBeerRatingDto {

    private UUID beerId;
    private Double score;
    private Double sweetness;
    private Double bitterness;
    private Double dryness;
    private Double sour;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompoundBeerRatingDto that = (CompoundBeerRatingDto) o;
        return Objects.equals(beerId, that.beerId) &&
                Objects.equals(score, that.score) &&
                Objects.equals(sweetness, that.sweetness) &&
                Objects.equals(bitterness, that.bitterness) &&
                Objects.equals(dryness, that.dryness) &&
                Objects.equals(sour, that.sour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beerId, score, sweetness, bitterness, dryness, sour);
    }

    public UUID getBeerId() {
        return beerId;
    }

    public void setBeerId(UUID beerId) {
        this.beerId = beerId;
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
