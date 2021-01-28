package com.brewed.core.rest.beer;

import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.beer.BeerDto;
import com.brewed.core.model.beer.BeerNote;
import com.brewed.core.model.beer.BeerType;
import com.brewed.core.model.brewery.Brewery;
import com.brewed.core.rest.beernote.BeerNoteService;
import com.brewed.core.rest.beertype.BeerTypeService;
import com.brewed.core.rest.brewery.BreweryService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BeerControllerIT {

    public static final String BAR_CODE = "1845678901001";
    @Autowired
    BeerService beerService;
    @Autowired
    BeerNoteService beerNoteService;
    @Autowired
    BreweryService breweryService;
    @Autowired
    BeerTypeService beerTypeService;
    @Autowired
    private MockMvc mockMvc;
    private Brewery brewery;
    private BeerNote beerNote;
    private BeerType beerType;


    @BeforeEach
    void resetDatabase() {
        Brewery tempBrewery = new Brewery();
        tempBrewery.setAddress("Sample address");
        tempBrewery.setName("Sample name");
        tempBrewery.setDescription("Sample description");
        brewery = breweryService.addBrewery(tempBrewery);
        beerNote = beerNoteService.create("Sample note");
        beerType = beerTypeService.create("Sample type");
        beerService.deleteAll();
    }

    @Test
    void shouldGetBeerFromId() throws Exception {
        BeerDto beer = new BeerDto();
        beer.setAlcoholPercentage(5.5);
        beer.setBarCode(BAR_CODE);
        BeerType beerType = beerTypeService.create("Ale");
        beer.setBeerTypeId(beerType.getId());
        String note = "None";
        BeerNote beerNote = beerNoteService.create(note);
        beer.setBeerNoteId(beerNote.getId());
        Brewery brewery = new Brewery();
        brewery.setName("Lech");
        brewery.setDescription("We produce very good beer");
        brewery.setAddress("Poznan");
        Brewery domainBrewery = breweryService.addBrewery(brewery);
        beer.setBreweryId(domainBrewery.getId());
        beer.setPrice(5.50);
        beer.setAlcoholPercentage(4.2);
        Beer resultBeer = beerService.addBeer(beer);
        mockMvc.perform(get("/api/beer/" + resultBeer.getId()
                                                     .toString()).secure(true)
                                                                 .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value(beer.getName()));
    }

    @Test
    void shouldGetBeerFromName() throws Exception {
        BeerDto beer = new BeerDto();
        beer.setAlcoholPercentage(5.5);
        beer.setBarCode(BAR_CODE);
        beer.setBeerTypeId(beerType.getId());
        beer.setBeerNoteId(beerNote.getId());
        beer.setBreweryId(brewery.getId());
        beer.setName("Sample Name");
        Beer beer1 = beerService.addBeer(beer);
        mockMvc.perform(get("/api/beer?name=" + beer.getName()).secure(true))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(beer1.getId()
                                                         .toString()));
    }

    @Test
    void shouldCallGetAllBeers() throws Exception {
        mockMvc.perform(get("/api/beer").secure(true))
               .andExpect(status().isOk());
    }

    @WithMockUser(value = "spring")
    @Test
    void shouldCallServiceAddMethodWithValidParams() throws Exception {
        BeerDto beer = new BeerDto();
        beer.setAlcoholPercentage(5.5);
        beer.setBarCode(BAR_CODE);
        beer.setBeerTypeId(beerType.getId());
        beer.setBeerNoteId(beerNote.getId());
        beer.setBreweryId(brewery.getId());
        Gson gson = new Gson();
        String beerJson = gson.toJson(beer);
        mockMvc.perform(post("/api/beer/add").secure(true)
                                             .content(beerJson)
                                             .contentType(MediaType.APPLICATION_JSON)
                                             .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated());
    }

    @WithMockUser(value = "spring")
    @Test
    void shouldCallServiceUpdateMethodWithValidParams() throws Exception {
        BeerDto beer = new BeerDto();
        beer.setAlcoholPercentage(5.5);
        beer.setBarCode(BAR_CODE);
        beer.setBeerTypeId(beerType.getId());
        beer.setBeerNoteId(beerNote.getId());
        beer.setBreweryId(brewery.getId());
        Beer persistedBeer = beerService.addBeer(beer);
        assertThat(persistedBeer.getName()).isNull();
        Gson gson = new Gson();
        beer.setId(persistedBeer.getId());
        String modifiedBeer = "Modified BeerDto";
        beer.setName(modifiedBeer);
        String beerJson = gson.toJson(beer);
        mockMvc.perform(put("/api/beer").secure(true)
                                        .content(beerJson)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value(modifiedBeer));
    }

    @WithMockUser(value = "spring")
    @Test
    void shouldCallServiceRemoveMethodWithValidParams() throws Exception {
        BeerDto beerDto = new BeerDto();
        beerDto.setBeerTypeId(beerType.getId());
        beerDto.setBeerNoteId(beerNote.getId());
        beerDto.setBreweryId(brewery.getId());
        Beer beer = beerService.addBeer(beerDto);
        mockMvc.perform(
                delete("/api/beer/delete/" + beer.getId()).secure(true))
               .andDo(print())
               .andExpect(status().isOk());
    }

    @WithMockUser(value = "spring")
    @Test
    void shouldCallServiceAddAllBeersWithValidParams() throws Exception {
        List<Beer> beers = BeerTestUtils.generateRandomBeers(3);
        Gson gson = new Gson();
        String beersInJson = gson.toJson(beers);
        mockMvc.perform(post("/api/beer/addAll").secure(true)
                                                .content(beersInJson)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated());
    }

}
