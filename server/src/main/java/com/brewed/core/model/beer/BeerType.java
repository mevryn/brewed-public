package com.brewed.core.model.beer;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class BeerType {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String type;


    public BeerType(UUID beerTypeId) {
        this.id = beerTypeId;
    }

    public BeerType(String type) {
        this.type = type;
    }

    public BeerType() {

    }

    public BeerType(UUID id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeerType beerType = (BeerType) o;
        return Objects.equals(id, beerType.id) && Objects.equals(type, beerType.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

