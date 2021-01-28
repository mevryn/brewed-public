package com.brewed.core.model.beercharatings;

import com.brewed.core.model.beer.Beer;

public class CompoundBeerRating {
    private Double score;
    private Double sweetness;
    private Double bitterness;
    private Double dryness;
    private Double sour;
    private Beer beer;

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

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }
}
