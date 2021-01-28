package com.brewed.core.rest.beernote;


import com.brewed.core.model.ModificationEnvelope;
import com.brewed.core.model.beer.BeerNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/beer-note")
public class BeerNoteController {


    @Autowired
    private BeerNoteService beerNoteService;

    @GetMapping
    public Optional<BeerNote> getBeerNote(@RequestParam(name = "name") String name) {
        return beerNoteService.getBeerNoteByName(name);
    }

    @GetMapping("/all")
    public Collection<BeerNote> getAllBeerNotes() {
        return beerNoteService.getAllBeerNotes();
    }

    @GetMapping("/{id}")
    public Optional<BeerNote> getBeerNoteById(@PathVariable(name = "id") String id) {
        return beerNoteService.getBeerNoteById(id);
    }

    @PostMapping("/deleteGiven")
    public void deleteAllBeers(@RequestBody Collection<BeerNote> beerNotes) {
        beerNoteService.deleteAll(beerNotes);
    }

    @PostMapping("/create")
    public BeerNote create(@RequestBody String note) {
        return beerNoteService.create(note);
    }

    @PostMapping
    public BeerNote create(@RequestBody BeerNote note) {
        return beerNoteService.updateNote(note);
    }


    @PutMapping
    public BeerNote update(@RequestBody ModificationEnvelope modificationEnvelope) {
        return beerNoteService.modifyBeerNote(modificationEnvelope);
    }

    @DeleteMapping("/{note}")
    public void deleteBeer(@PathVariable("note") String id) {
        beerNoteService.delete(UUID.fromString(id));
    }
}
