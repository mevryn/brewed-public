package com.brewed.core.rest.brewery;

import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.brewery.Brewery;
import com.brewed.core.rest.beer.BeerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BreweryServiceTest {
    @Mock
    private BreweryRepository breweryRepository;
    @InjectMocks
    private BreweryService breweryService;
    @Mock
    private BeerRepository beerRepository;

    @Test
    void shouldAddAllProvidedBreweriesToRepo() {
        List<Brewery> breweries = BreweryTestUtils.generateRandomBreweries(3);
        breweryService.addProvidedBreweries(breweries);
        verify(breweryRepository).saveAll(breweries);
    }

    @Test
    void shouldGetBreweryWithProvidedId() {
        UUID uuid = UUID.randomUUID();
        breweryService.getBrewery(uuid);
        verify(breweryRepository).findById(uuid);
    }

    @Test
    void shouldAddProvidedBrewery() {
        Brewery brewery = BreweryTestUtils.generateRandomBrewery();
        breweryService.addBrewery(brewery);
        verify(breweryRepository).saveAndFlush(brewery);
    }

    @Test
    void shouldUpdateProvidedBrewery() {
        Brewery brewery = BreweryTestUtils.generateRandomBrewery();
        brewery.setId(UUID.randomUUID());
        when(breweryRepository.existsById(brewery.getId())).thenReturn(true);
        breweryService.update(brewery);
        verify(breweryRepository).save(brewery);
    }

    @Test
    void shouldRemoveProvidedBreweries() {
        UUID uuid = UUID.randomUUID();
        Brewery brewery = BreweryTestUtils.generateRandomBrewery();
        brewery.setId(uuid);
        breweryService.delete(uuid);
        verify(breweryRepository).deleteById(uuid);
    }

    @Test
    void shouldGetAllBreweries() {
        breweryService.getAllBreweries();
        verify(breweryRepository).findAll();
    }


    @Test
    void shouldDeleteBreweryWithoutBeers() {
        Brewery brewery = new Brewery();
        Beer beer = new Beer();
        beer.setName("BeerDto");
        brewery.getCraftedBeers()
               .add(beer);
        UUID id = UUID.randomUUID();
        brewery.setId(id);
        breweryService.delete(brewery.getId());
        verify(beerRepository).saveAll(List.of());
        verify(breweryRepository).deleteById(id);
    }

    @Test
    void shouldGetBreweryById() {
        Brewery brewery = new Brewery();
        brewery.setId(UUID.randomUUID());
        when(breweryRepository.findById(brewery.getId()))
                .thenReturn(Optional.of(brewery));
        Optional<Brewery> result = breweryService.getBrewery(brewery.getId());
        verify(breweryRepository).findById(brewery.getId());
        assertThat(result).isPresent();
        assertThat(result).contains(brewery);
    }

    @Test
    void shouldGetBreweryByName() {
        Brewery brewery = new Brewery();
        brewery.setId(UUID.randomUUID());
        when(breweryRepository.findByName(brewery.getName()))
                .thenReturn(Optional.of(brewery));
        Optional<Brewery> result = breweryService.getBrewery(brewery.getName());
        verify(breweryRepository).findByName(brewery.getName());
        assertThat(result).isPresent();
        assertThat(result).contains(brewery);
    }

    @Test
    void shouldDeleteAll() {
        breweryService.deleteAll();
        verify(breweryRepository).deleteAll(List.of());
    }
}
