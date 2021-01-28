package com.brewed.core.rest.brewery;

import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.beer.BeerDto;
import com.brewed.core.model.beer.BeerNote;
import com.brewed.core.model.beer.BeerType;
import com.brewed.core.model.brewery.Brewery;
import com.brewed.core.rest.beer.BeerService;
import com.brewed.core.rest.beernote.BeerNoteController;
import com.brewed.core.rest.beertype.BeerTypeController;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(PER_CLASS)
class BreweryControllerIT {

    @Autowired
    PlatformTransactionManager platformTransactionManager;
    TransactionTemplate transactionTemplate;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BeerService beerService;
    @Autowired
    private BeerNoteController beerNoteController;
    @Autowired
    private BeerTypeController beerTypeController;
    @Autowired
    private BreweryService breweryService;
    @Autowired
    private BreweryController breweryController;
    @Autowired
    private BreweryRepository breweryRepository;
    @Autowired
    private EntityManager entityManager;

    @BeforeAll
    void beforeAllTests() {
        transactionTemplate = new TransactionTemplate(platformTransactionManager);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                entityManager.createNativeQuery(
                        "INSERT into BREWERY(id, name) values ('e96c7de2d3c74121b14ff408c5cb2b77','Brak informacji')")
                             .executeUpdate();
            }
        });
    }

    @BeforeEach
    void setUp() {
        breweryController.deleteAllBreweries();
        beerService.deleteAll();


    }

    @Test
    void shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/brewery/get/" + UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteBrewery() throws Exception {
        Brewery brewery1 = new Brewery("Sample brewery 1");
        breweryController.addBrewery(brewery1);
        Optional<Brewery> breweryByName = breweryController.getBreweries(brewery1.getName())
                                                           .stream()
                                                           .findAny();
        mockMvc.perform(delete("/api/brewery/" + breweryByName.orElseThrow()
                                                              .getId()))
               .andDo(print())
               .andExpect(status().isOk());
        assertThat(breweryController.getBreweries(null)
                                    .size()).isEqualTo(1);
    }

    @Test
    void shouldThrowNotFoundIfDeletedBreweryNotFound() throws Exception {
        mockMvc.perform(delete("/api/brewery/delete/" + UUID.randomUUID()))
               .andDo(print())
               .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAll() throws Exception {
        Brewery brewery1 = new Brewery("Sample brewery 1");
        Brewery brewery2 = new Brewery("Sample brewery 2");
        breweryController.addBrewery(brewery1);
        breweryController.addBrewery(brewery2);
        assertThat(breweryController.getBreweries(null)
                                    .size()).isEqualTo(3);
        mockMvc.perform(delete("/api/brewery/delete/all"))
               .andDo(print())
               .andExpect(status().isOk());
        assertThat(breweryController.getBreweries(null)
                                    .size()).isEqualTo(1);
    }

    @Test
    void shouldNotDeleteAllIfNotFound() throws Exception {
        mockMvc.perform(delete("/api/brewery/delete/all"))
               .andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    void shouldCreateBrewery() throws Exception {
        Brewery brewery = new Brewery();
        brewery.setName("Sample brewery name");
        Gson gson = new Gson();
        String beerJson = gson.toJson(brewery);
        mockMvc.perform(post("/api/brewery").content(beerJson)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isCreated());
        Brewery breweryFromDB = breweryController.getBreweries("Sample brewery name")
                                                 .stream()
                                                 .findAny()
                                                 .orElseThrow();
        assertThat(breweryFromDB.getName()).isEqualTo(brewery.getName());
    }

    @Test
    void shouldCreateProvidedBreweries() throws Exception {
        List<Brewery> breweryList = new ArrayList<>();
        Brewery brewery = new Brewery();
        brewery.setName("Sample brewery name");
        breweryList.add(brewery);
        breweryList.add(brewery);
        Gson gson = new Gson();
        String listJson = gson.toJson(breweryList);

        mockMvc.perform(post("/api/brewery/add/all").content(listJson)
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk());
        List<Brewery> allBreweries = breweryController.getBreweries(null);
        assertThat(allBreweries).isNotEmpty();
        assertThat(allBreweries.size()).isEqualTo(3);
    }

    @Test
    void shouldUpdateExistingBrewery() throws Exception {
        Brewery brewery = new Brewery();
        brewery.setName("Sample brewery name");
        breweryController.addBrewery(brewery);
        Brewery breweryByName = breweryController.getBreweries(brewery.getName())
                                                 .stream()
                                                 .findAny()
                                                 .orElseThrow();
        String address = "Skrajna 13";
        breweryByName.setAddress(address);
        Gson gson = new Gson();
        String breweryJson = gson.toJson(breweryByName);
        mockMvc.perform(put("/api/brewery").content(breweryJson)
                                           .contentType(MediaType.APPLICATION_JSON)
                                           .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk());
        UUID id = breweryByName.getId();
        assertThat(breweryController.getBrewery(id)).isEqualTo(breweryByName);
    }

    @Test
    void shouldFindBreweryWithName() throws Exception {
        Brewery brewery = new Brewery();
        brewery.setName("Sample brewery name");
        brewery.setId(UUID.randomUUID());
        String address = "Skrajna 13";
        brewery.setAddress(address);
        Gson gson = new Gson();
        String breweryJson = gson.toJson(brewery);
        mockMvc.perform(put("/api/brewery").content(breweryJson)
                                           .contentType(MediaType.APPLICATION_JSON)
                                           .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isNotFound());
    }

    @Test
    void shouldThrowNotFoundWhileTryingToUpdateNotExistingElement() throws Exception {
        Brewery brewery = new Brewery();
        brewery.setName("Sample brewery name");
        brewery.setId(UUID.randomUUID());
        String address = "Skrajna 13";
        brewery.setAddress(address);
        Gson gson = new Gson();
        String breweryJson = gson.toJson(brewery);
        mockMvc.perform(put("/api/brewery").content(breweryJson)
                                           .contentType(MediaType.APPLICATION_JSON)
                                           .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteOnlyBreweryWithoutBeer() throws Exception {
        String sampleBreweryName = "Sample brewery name";
        BeerNote sampleNote = beerNoteController.create("Sample note");
        BeerType beerType = beerTypeController.create("Sample type");
        Brewery brewery = new Brewery();
        brewery.setName(sampleBreweryName);
        Brewery responseBrewery = breweryService.addBrewery(brewery);
        BeerDto beerDto = new BeerDto();
        beerDto.setName("BeerDto");
        beerDto.setBreweryId(brewery.getId());
        beerDto.setBeerTypeId(beerType.getId());
        beerDto.setBeerNoteId(sampleNote.getId());
        Beer beer = beerService.addBeer(beerDto);
        mockMvc.perform(delete("/api/brewery/" + responseBrewery.getId()))
               .andDo(print())
               .andExpect(status().isOk());
        assertAll(() -> assertThat(beerService.findById(beer.getId())).isNotEmpty(),
                  () -> assertThat(breweryService.getBrewery(responseBrewery.getId())).isEmpty());
    }
}
