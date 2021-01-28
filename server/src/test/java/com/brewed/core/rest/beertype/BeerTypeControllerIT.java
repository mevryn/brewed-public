package com.brewed.core.rest.beertype;


import com.brewed.core.model.ModificationEnvelope;
import com.brewed.core.model.beer.BeerType;
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
class BeerTypeControllerIT {


    @Autowired
    private BeerTypeService service;

    @Autowired
    private BeerTypeRepository repository;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void setUp() {
        repository.deleteAll();
    }


    @Test
    void shouldGetType() throws Exception {
        String sampleType = "Sample type";
        service.create(sampleType);
        service.getBeerType(sampleType);
        mockMvc.perform(get("/api/beer-type?name=" + sampleType))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.type").value(sampleType));
    }


    @Test
    void shouldCreateBeerType() throws Exception {
        String sampleType = "Sample type";
        mockMvc.perform(post("/api/beer-type/create").content(sampleType))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.type").value(sampleType));
        BeerType beerType = service.getBeerType(sampleType)
                                   .orElseThrow();
        assertThat(beerType
                           .getType()).isEqualTo(sampleType);
    }

    @Test
    void shouldUpdate() throws Exception {
        String sampleType = "Sample type";
        mockMvc.perform(post("/api/beer-type/create").contentType(MediaType.APPLICATION_JSON)
                                                     .content(sampleType))
               .andDo(print())
               .andExpect(status().isOk());
        String modifiedType = "Modified type";
        ModificationEnvelope modificationEnvelope = new ModificationEnvelope(sampleType, modifiedType);
        Gson gson = new Gson();
        String updatedBeerTypeJson = gson.toJson(modificationEnvelope);
        mockMvc.perform(put("/api/beer-type").contentType(MediaType.APPLICATION_JSON)
                                             .content(updatedBeerTypeJson))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.type").value(modifiedType));
        BeerType beerTypeAfterModification = service.getBeerType(modifiedType)
                                                    .orElseThrow();
        assertThat(beerTypeAfterModification
                           .getType()).isEqualTo(modifiedType);
    }

    @Test
    void shouldDelete() throws Exception {
        String sampleType = "Sample type";
        BeerType beerType = service.create(sampleType);
        assertThat(service.getBeerType(sampleType)).isPresent();
        mockMvc.perform(delete("/api/beer-type/" + beerType.getId()))
               .andDo(print())
               .andExpect(status().isOk());
        assertThat(service.getBeerType(sampleType)).isEmpty();
    }
}
