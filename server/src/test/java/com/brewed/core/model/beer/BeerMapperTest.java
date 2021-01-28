package com.brewed.core.model.beer;

import com.brewed.core.model.brewery.Brewery;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class BeerMapperTest {

    @Test
    void shouldMapToDto() {
        Beer beer = new Beer();
        beer.setPrice(12.5);
        beer.setAlcoholPercentage(0.5);
        beer.setName("Kotowy");
        beer.setBeerType(new BeerType("Ale"));
        beer.setBarCode("523452345341");
        Brewery brewery = new Brewery();
        brewery.setName("Kociniec");
        brewery.setDescription("Good beers");
        brewery.setAddress("KotowoCity 22");
        brewery.setId(UUID.randomUUID());
        beer.setBrewery(brewery);
        beer.setId(UUID.randomUUID());
        beer.setBeerNote(new BeerNote("Honey"));
        BeerMapper beerMapper = new BeerMapper();
        BeerDto beerDto = beerMapper.toDto(beer);
        assertAll(() -> assertThat(beerDto.getAlcoholPercentage()).isEqualTo(beer.getAlcoholPercentage()),
                  () -> assertThat(beerDto.getBarCode()).isEqualTo(beer.getBarCode()),
                  () -> assertThat(beerDto.getBeerTypeId()).isEqualTo(beer.getBeerType()
                                                                          .getId()),
                  () -> assertThat(beerDto.getBeerNoteId()).isEqualTo(beer.getBeerNote()
                                                                          .getId()),
                  () -> assertThat(beerDto.getBreweryId()).isEqualTo(beer.getBrewery()
                                                                         .getId()),
                  () -> assertThat(beerDto.getId()).isEqualTo(beer.getId()),
                  () -> assertThat(beerDto.getName()).isEqualTo(beer.getName()));
    }

    @Test
    void shouldMapToDomain() {
        BeerDto dto = new BeerDto();
        dto.setPrice(12.5);
        dto.setAlcoholPercentage(0.5);
        UUID beerTypeId = UUID.randomUUID();
        dto.setBeerTypeId(beerTypeId);
        dto.setBarCode("523452345341");
        UUID breweryId = UUID.randomUUID();
        dto.setBreweryId(breweryId);
        dto.setId(UUID.randomUUID());
        dto.setName("Sample beer");
        UUID uuid = UUID.randomUUID();
        dto.setBeerNoteId(uuid);
        BeerMapper beerMapper = new BeerMapper();
        Beer beer = beerMapper.toDomain(dto);
        assertAll(() -> assertThat(beer.getAlcoholPercentage()).isEqualTo(dto.getAlcoholPercentage()),
                  () -> assertThat(beer.getBarCode()).isEqualTo(dto.getBarCode()),
                  () -> assertThat(beer.getName()).isEqualTo(dto.getName()),
                  () -> assertThat(beer.getBeerType()
                                       .getId()).isEqualTo(dto
                                                                   .getBeerTypeId()),
                  () -> assertThat(beer.getBeerNote()
                                       .getId()).isEqualTo(dto
                                                                   .getBeerNoteId()),
                  () -> assertThat(beer.getBrewery()
                                       .getId()).isEqualTo(dto.getBreweryId()),
                  () -> assertThat(beer.getId()).isEqualTo(dto.getId()),
                  () -> assertThat(beer.getName()).isEqualTo(dto.getName()));
    }
}
