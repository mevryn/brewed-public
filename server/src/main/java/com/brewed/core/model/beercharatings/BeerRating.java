package com.brewed.core.model.beercharatings;

import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.useridentifier.UserIdentifier;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class BeerRating {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    private UserIdentifier userIdentifier;
    private Double score;
    private Double sweetness;
    private Double bitterness;
    private Double dryness;
    private Double sour;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    private Beer beer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeerRating that = (BeerRating) o;
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

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    public UserIdentifier getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(UserIdentifier userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
