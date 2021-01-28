package com.brewed.core.rest.beernote;

import com.brewed.core.model.ModificationEnvelope;
import com.brewed.core.model.beer.BeerNote;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class BeerNoteControllerIT {
    @Autowired
    private BeerNoteService service;

    @Autowired
    private BeerNoteRepository repository;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void setUp() {
        repository.deleteAll();
    }


    @Test
    void shouldGetByName() throws Exception {
        String sampleNote = "Sample Note";
        service.create(sampleNote);
        mockMvc.perform(get("/api/beer-note?name=" + sampleNote))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.note").value(sampleNote));
    }


    @Test
    void shouldCreateBeerNote() throws Exception {
        String sampleNote = "Sample Note";
        mockMvc.perform(post("/api/beer-note/create").content(sampleNote))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.note").value(sampleNote));
        BeerNote beerNote = service.getBeerNoteByName(sampleNote)
                                   .orElseThrow();
        assertThat(beerNote
                           .getNote()).isEqualTo(sampleNote);
    }

    @Test
    void shouldUpdate() throws Exception {
        String sampleNote = "Sample Note";
        String modifiedNote = "Modified Note";
        service.create(sampleNote);
        ModificationEnvelope envelope = new ModificationEnvelope(sampleNote, modifiedNote);
        Gson gson = new Gson();
        String envelopeJson = gson.toJson(envelope);
        mockMvc.perform(put("/api/beer-note").contentType(MediaType.APPLICATION_JSON)
                                             .content(envelopeJson))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.note").value(modifiedNote));
        BeerNote beerNoteAfterModification = service.getBeerNoteByName(modifiedNote)
                                                    .orElseThrow();
        assertThat(beerNoteAfterModification).isNotNull();
        assertThat(beerNoteAfterModification
                           .getNote()).isEqualTo(modifiedNote);
    }

    @Test
    void shouldDelete() throws Exception {
        String sampleNote = "Sample Note";
        BeerNote beerNote = service.create(sampleNote);
        assertThat(service.getBeerNoteByName(sampleNote)).isPresent();
        mockMvc.perform(delete("/api/beer-note/" + beerNote.getId()))
               .andDo(print())
               .andExpect(status().isOk());
        assertThat(service.getBeerNoteByName(sampleNote)).isEmpty();
    }
}
