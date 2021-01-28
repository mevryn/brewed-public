package com.brewed.core.model.beer;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class BeerNote {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String note;

    public BeerNote() {
    }

    public BeerNote(UUID id, String note) {
        this.id = id;
        this.note = note;
    }

    public BeerNote(String note) {
        this.note = note;
    }

    public BeerNote(UUID noteTypeId) {
        this.id = noteTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeerNote beerNote = (BeerNote) o;
        return Objects.equals(id, beerNote.id) && Objects.equals(note, beerNote.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, note);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
