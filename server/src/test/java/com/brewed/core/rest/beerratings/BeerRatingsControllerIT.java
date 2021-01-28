package com.brewed.core.rest.beerratings;

import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.beercharatings.BeerRating;
import com.brewed.core.model.beercharatings.BeerRatingDto;
import com.brewed.core.model.beercharatings.BeerRatingMapper;
import com.brewed.core.rest.beer.BeerRepository;
import com.brewed.core.rest.exception.NotFoundException;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BeerRatingsControllerIT {


    @Autowired
    private BeerRepository beerRepository;


    @Autowired
    private BeerRatingsRepository beerRatingsRepository;

    @Autowired
    private BeerRatingsService ratingsService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        beerRatingsRepository.deleteAll();
        beerRepository.deleteAll();
    }

    @Test
    void shouldGetBeerRatingForUser() throws Exception {

        Beer beer = beerRepository.saveAndFlush(new Beer());
        BeerRatingDto dto = new BeerRatingDto();
        dto.setBeer(beer.getId());
        double bitterness = 0.76;
        dto.setBitterness(bitterness);
        double sour = 0.66;
        dto.setSour(sour);
        double dryness = 0.46;
        dto.setDryness(dryness);
        double sweetness = 0.2;
        dto.setSweetness(sweetness);
        double score = 0.8;
        dto.setScore(score);
        String identifier = "Test";
        dto.setUserIdentifier(identifier);
        Gson gson = new Gson();
        String dtoJson = gson.toJson(dto);
        ratingsService.postRating(dto);
        mockMvc.perform(get("/api/beer-ratings?beerId=" + beer.getId() + "&userId=" + identifier).secure(true)
                                                                                                 .contentType(
                                                                                                         MediaType.APPLICATION_JSON)
                                                                                                 .content(dtoJson))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.userIdentifier").value(identifier));

    }

    @Test
    void shouldGetAllRatingsForUser() throws Exception {

        Beer beer1 = beerRepository.saveAndFlush(new Beer());
        Beer beer = beerRepository.saveAndFlush(new Beer());
        BeerRatingDto dto = new BeerRatingDto();
        dto.setBeer(beer.getId());
        double bitterness = 0.76;
        dto.setBitterness(bitterness);
        double sour = 0.66;
        dto.setSour(sour);
        double dryness = 0.46;
        dto.setDryness(dryness);
        double sweetness = 0.2;
        dto.setSweetness(sweetness);
        double score = 0.8;
        dto.setScore(score);
        String identifier = "Test";
        dto.setUserIdentifier(identifier);
        Gson gson = new Gson();
        String dtoJson = gson.toJson(dto);
        ratingsService.postRating(dto);
        dto.setBeer(beer1.getId());
        ratingsService.postRating(dto);
        mockMvc.perform(get("/api/beer-ratings/all?userId=" + identifier).secure(true)
                                                                         .contentType(
                                                                                 MediaType.APPLICATION_JSON)
                                                                         .content(dtoJson))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].userIdentifier").value(identifier))
               .andExpect(jsonPath("$[1].userIdentifier").value(identifier));
        List<BeerRatingDto> allRatingsForUser = ratingsService.getAllRatingsForUser(identifier);
        assertThat(allRatingsForUser).isNotEmpty();
        assertThat(allRatingsForUser.size()).isEqualTo(2);
    }

    @Test
    void shouldThrowBadRequestIfUserIdParamOfGetParamNotPresent() throws Exception {

        Beer beer = beerRepository.saveAndFlush(new Beer());
        BeerRatingDto dto = new BeerRatingDto();
        dto.setBeer(beer.getId());
        double bitterness = 0.76;
        dto.setBitterness(bitterness);
        double sour = 0.66;
        dto.setSour(sour);
        double dryness = 0.46;
        dto.setDryness(dryness);
        double sweetness = 0.2;
        dto.setSweetness(sweetness);
        double score = 0.8;
        dto.setScore(score);
        String identifier = "Test";
        dto.setUserIdentifier(identifier);
        Gson gson = new Gson();
        String dtoJson = gson.toJson(dto);
        ratingsService.postRating(dto);
        mockMvc.perform(get("/api/beer-ratings?beerId=" + beer.getId()).secure(true)
                                                                       .contentType(
                                                                               MediaType.APPLICATION_JSON)
                                                                       .content(dtoJson))
               .andDo(print())
               .andExpect(status().isBadRequest());

    }

    @Test
    void shouldThrowBadRequestIfBeerIdIdParamOfGetParamNotPresent() throws Exception {

        Beer beer = beerRepository.saveAndFlush(new Beer());
        BeerRatingDto dto = new BeerRatingDto();
        dto.setBeer(beer.getId());
        double bitterness = 0.76;
        dto.setBitterness(bitterness);
        double sour = 0.66;
        dto.setSour(sour);
        double dryness = 0.46;
        dto.setDryness(dryness);
        double sweetness = 0.2;
        dto.setSweetness(sweetness);
        double score = 0.8;
        dto.setScore(score);
        String identifier = "Test";
        dto.setUserIdentifier(identifier);
        Gson gson = new Gson();
        String dtoJson = gson.toJson(dto);
        ratingsService.postRating(dto);
        mockMvc.perform(get("/api/beer-ratings?userId=" + identifier).secure(true)
                                                                     .contentType(
                                                                             MediaType.APPLICATION_JSON)
                                                                     .content(dtoJson))
               .andDo(print())
               .andExpect(status().isBadRequest());

    }


    @Test
    void shouldThrowNotFoundIfBeerRatingNotFound() throws Exception {

        Beer beer = beerRepository.saveAndFlush(new Beer());
        BeerRatingDto dto = new BeerRatingDto();
        dto.setBeer(beer.getId());
        double bitterness = 0.76;
        dto.setBitterness(bitterness);
        double sour = 0.66;
        dto.setSour(sour);
        double dryness = 0.46;
        dto.setDryness(dryness);
        double sweetness = 0.2;
        dto.setSweetness(sweetness);
        double score = 0.8;
        dto.setScore(score);
        String identifier = "Test";
        dto.setUserIdentifier(identifier);
        Gson gson = new Gson();
        String dtoJson = gson.toJson(dto);
        mockMvc.perform(get("/api/beer-ratings?beerId=" + beer.getId() + "&userId=" + identifier).secure(true)
                                                                                                 .contentType(
                                                                                                         MediaType.APPLICATION_JSON)
                                                                                                 .content(dtoJson))
               .andDo(print())
               .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateBeerCharacteristicsWithoutAuthorization() throws Exception {
        Beer beer = beerRepository.saveAndFlush(new Beer());
        BeerRatingDto dto = new BeerRatingDto();
        dto.setBeer(beer.getId());
        double bitterness = 0.76;
        dto.setBitterness(bitterness);
        double sour = 0.66;
        dto.setSour(sour);
        double dryness = 0.46;
        dto.setDryness(dryness);
        double sweetness = 0.2;
        dto.setSweetness(sweetness);
        double score = 0.8;
        dto.setScore(score);
        String identifier = "Test";
        dto.setUserIdentifier(identifier);
        Gson gson = new Gson();
        String dtoJson = gson.toJson(dto);
        mockMvc.perform(put("/api/beer-ratings").secure(true)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(dtoJson))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.userIdentifier.identifier").value(identifier));
    }

    @Test
    void shouldUserCanDeleteHisOwnBeerRatings() throws Exception {
        Beer beer = beerRepository.saveAndFlush(new Beer());
        BeerRatingDto dto = new BeerRatingDto();
        dto.setBeer(beer.getId());
        double bitterness = 0.76;
        dto.setBitterness(bitterness);
        double sour = 0.66;
        dto.setSour(sour);
        double dryness = 0.46;
        dto.setDryness(dryness);
        double sweetness = 0.2;
        dto.setSweetness(sweetness);
        double score = 0.8;
        dto.setScore(score);
        String identifier = "Test";
        dto.setUserIdentifier(identifier);
        BeerRating beerRating = ratingsService.postRating(dto);
        BeerRatingMapper beerRatingMapper = new BeerRatingMapper();
        UUID beerId = dto.getBeer();
        BeerRatingDto beerRatingsForUser = ratingsService.getBeerRatingsForUser(beerId, dto.getUserIdentifier());
        assertAll(() -> assertThat(beerRatingsForUser).isNotNull(),
                  () -> assertThat(beerRatingsForUser).isEqualTo(
                          beerRatingMapper.toDto(beerRating)));
        mockMvc.perform(
                delete("/api/beer-ratings?beerId=" + beerId + "&userId=" + dto.getUserIdentifier()).secure(true))
               .andExpect(status().isOk());
        String userIdentifier = dto.getUserIdentifier();
        assertThrows(NotFoundException.class,
                     () -> ratingsService.getBeerRatingsForUser(beerId, userIdentifier));
    }

    @Test
    void shouldThrowBeerNotFound() throws Exception {
        BeerRatingDto dto = new BeerRatingDto();
        dto.setBeer(UUID.randomUUID());
        double bitterness = 0.76;
        dto.setBitterness(bitterness);
        double sour = 0.66;
        dto.setSour(sour);
        double dryness = 0.46;
        dto.setDryness(dryness);
        double sweetness = 0.2;
        dto.setSweetness(sweetness);
        double score = 0.8;
        dto.setScore(score);
        String identifier = "Test";
        dto.setUserIdentifier(identifier);
        Gson gson = new Gson();
        String dtoJson = gson.toJson(dto);
        mockMvc.perform(put("/api/beer-ratings").secure(true)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(dtoJson))
               .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "spring")
    @Test
    void shouldGetBeerRankingByIdIfAuthenticatedAdmin() throws Exception {
        Beer beer = beerRepository.saveAndFlush(new Beer());
        BeerRatingDto dto = new BeerRatingDto();
        dto.setBeer(beer.getId());
        double bitterness = 0.76;
        dto.setBitterness(bitterness);
        double sour = 0.66;
        dto.setSour(sour);
        double dryness = 0.46;
        dto.setDryness(dryness);
        double sweetness = 0.2;
        dto.setSweetness(sweetness);
        double score = 0.8;
        dto.setScore(score);
        String identifier = "Test";
        dto.setUserIdentifier(identifier);
        BeerRating beerRating = ratingsService.postRating(dto);
        mockMvc.perform(get("/api/beer-ratings/" + beerRating.getId()).secure(true))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(beerRating.getId()
                                                           .toString()));
    }

    @Test
    void shouldThrowForbiddenForRequestingBeerRankingOnlyById() throws Exception {
        mockMvc.perform(get("/api/beer-ratings/" + UUID.randomUUID()).secure(true))
               .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldCreateMoreThanOneRatingForUser() throws Exception {

        Beer beer1 = beerRepository.saveAndFlush(new Beer());
        Beer beer2 = beerRepository.saveAndFlush(new Beer());
        BeerRatingDto dto = new BeerRatingDto();
        dto.setBeer(beer1.getId());
        double bitterness = 0.76;
        dto.setBitterness(bitterness);
        double sour = 0.66;
        dto.setSour(sour);
        double dryness = 0.46;
        dto.setDryness(dryness);
        double sweetness = 0.2;
        dto.setSweetness(sweetness);
        double score = 0.8;
        dto.setScore(score);
        String identifier = "Test";
        dto.setUserIdentifier(identifier);
        Gson gson = new Gson();
        String firstJson = gson.toJson(dto);
        dto.setBeer(beer2.getId());
        String secondJson = gson.toJson(dto);
        mockMvc.perform(put("/api/beer-ratings").secure(true)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(firstJson))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.userIdentifier.identifier").value(identifier));
        mockMvc.perform(put("/api/beer-ratings").secure(true)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(secondJson))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.userIdentifier.identifier").value(identifier));
        assertThat(ratingsService.getAllRatingsForUser(identifier)
                                 .size()).isEqualTo(2);
    }

    @Test
    void shouldGenerateCompoundBeerRating() throws Exception {
        Beer beer = beerRepository.saveAndFlush(new Beer());
        BeerRatingDto dto = new BeerRatingDto();
        dto.setBeer(beer.getId());
        for (int i = 0; i < 10; i++) {
            String identifier = "Test" + i;
            double bitterness = generateRandomPercent();
            dto.setBitterness(bitterness);
            double sour = generateRandomPercent();
            dto.setSour(sour);
            double dryness = generateRandomPercent();
            dto.setDryness(dryness);
            double sweetness = generateRandomPercent();
            dto.setSweetness(sweetness);
            double score = generateRandomPercent();
            dto.setScore(score);
            dto.setUserIdentifier(identifier);
            ratingsService.postRating(dto);
        }
        mockMvc.perform(get("/api/beer-ratings/compound?beerId=" + beer.getId()).secure(true))
               .andExpect(status().isOk())
               .andDo(print());
    }

    private double generateRandomPercent() {
        Random random = new Random();
        double leftLimit = 0D;
        double rightLimit = 1D;
        double result = leftLimit + random.nextDouble() * (rightLimit - leftLimit);
        BigDecimal roundedResult = new BigDecimal(Double.toString(result));
        roundedResult = roundedResult.setScale(2, RoundingMode.HALF_UP);
        return roundedResult.doubleValue();
    }


}
