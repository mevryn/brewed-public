package com.brewed.core.rest.beernote;

import com.brewed.core.model.ModificationEnvelope;
import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.beer.BeerNote;
import com.brewed.core.model.beer.BeerNoteConstants;
import com.brewed.core.rest.beer.BeerRepository;
import com.brewed.core.rest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class BeerNoteService {

    private final BeerNoteRepository beerNoteRepository;
    private final BeerRepository beerRepository;

    @Autowired
    public BeerNoteService(BeerNoteRepository beerNoteRepository, BeerRepository beerRepository) {
        this.beerNoteRepository = beerNoteRepository;
        this.beerRepository = beerRepository;
    }

    public BeerNote modifyBeerNote(ModificationEnvelope modificationEnvelope) {
        Optional<BeerNote> currentNote = beerNoteRepository.findByNote(modificationEnvelope.current);
        if (currentNote.isEmpty()) {
            throw new NotFoundException();
        } else {
            beerNoteRepository.delete(currentNote.get());
            return beerNoteRepository.save(new BeerNote(modificationEnvelope.modified));
        }
    }

    public void delete(UUID noteId) {
        if (noteId.equals(BeerNoteConstants.DEFAULT_BEER_NOTE.getId())) {
            throw new IllegalArgumentException("Default value can't be modified");
        }
        Collection<Beer> beerByBeerNote = beerRepository.findBeersByBeerNoteId(noteId);
        for (Beer beer : beerByBeerNote) {
            beer.setBeerNote(BeerNoteConstants.DEFAULT_BEER_NOTE);
        }
        beerRepository.saveAll(beerByBeerNote);
        beerNoteRepository.deleteById(noteId);
    }

    public BeerNote create(String note) {
        Optional<BeerNote> byName = beerNoteRepository.findByNote(note);
        return byName.orElseGet(() -> beerNoteRepository.save(new BeerNote(note)));
    }

    public BeerNote create(BeerNote beerNote) {
        return beerNoteRepository.saveAndFlush(beerNote);
    }


    public Optional<BeerNote> getBeerNoteByName(String name) {
        return beerNoteRepository.findByNote(name);
    }

    public Optional<BeerNote> getBeerNoteById(String id) {
        return beerNoteRepository.findById(UUID.fromString(id));
    }

    public Collection<BeerNote> getAllBeerNotes() {
        return beerNoteRepository.findAll();
    }

    public void deleteAll(Collection<BeerNote> beerNotes) {
        if (beerNotes.contains(BeerNoteConstants.DEFAULT_BEER_NOTE)) {
            throw new IllegalArgumentException("Default value can't be modified");
        }
        Collection<Beer> beerByBeerNote = beerRepository.findBeerByBeerNote(beerNotes);
        for (Beer beer : beerByBeerNote) {
            beer.setBeerNote(BeerNoteConstants.DEFAULT_BEER_NOTE);
        }
        beerRepository.saveAll(beerByBeerNote);
        beerNoteRepository.deleteAll(beerNotes);
    }

    public BeerNote updateNote(BeerNote note) {
        if (note.equals(BeerNoteConstants.DEFAULT_BEER_NOTE)) {
            throw new IllegalArgumentException("Default value can't be modified");
        }
        return beerNoteRepository.save(note);
    }
}
