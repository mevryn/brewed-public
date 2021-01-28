package com.brewed.core.rest.beernote;

import com.brewed.core.model.ModificationEnvelope;
import com.brewed.core.model.beer.BeerNote;
import com.brewed.core.rest.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeerNoteServiceTest {
    @Mock
    private BeerNoteRepository beerNoteRepository;
    @InjectMocks
    private BeerNoteService service;

    @Test
    void shouldGetByName() {
        String beerNoteName = "Sample beer note";
        BeerNote note = new BeerNote();
        UUID id = UUID.randomUUID();
        note.setId(id);
        service.getBeerNoteByName(beerNoteName);
        verify(beerNoteRepository).findByNote(beerNoteName);
    }


    @Test
    void shouldCreate() {
        String beerNoteName = "Sample beer note";
        service.create(beerNoteName);
        verify(beerNoteRepository).save(new BeerNote(beerNoteName));
    }

    @Test
    void shouldRemove() {
        UUID noteId = UUID.randomUUID();
        service.delete(noteId);
        verify(beerNoteRepository).deleteById(noteId);
    }

    @Test
    void shouldUpdateBeerNote() {
        String currentNote = "Sample beer note";
        BeerNote currentBeerNote = new BeerNote(currentNote);
        String modifiedNote = "Modified beer note";
        ModificationEnvelope modificationEnvelope = new ModificationEnvelope(currentNote, modifiedNote);
        when(beerNoteRepository.findByNote(modificationEnvelope.current))
                .thenReturn(Optional.of(currentBeerNote));
        service.modifyBeerNote(modificationEnvelope);
        verify(beerNoteRepository).delete(currentBeerNote);
        verify(beerNoteRepository).save(new BeerNote(modifiedNote));
    }

    @Test
    void shouldThrowBeerNoteNotPresentWhileUpdating() {
        String beerNoteName = "Sample beer note";
        String modifiedBeerNote = "Sample beer note";
        ModificationEnvelope modificationEnvelope = new ModificationEnvelope(beerNoteName, modifiedBeerNote);
        lenient().when(beerNoteRepository.findByNote(modificationEnvelope.current))
                 .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.modifyBeerNote(modificationEnvelope));
    }
}
