package com.brewed.core.rest.beer;

import com.brewed.core.model.beer.*;
import com.brewed.core.model.brewery.Brewery;
import com.brewed.core.rest.beernote.BeerNoteRepository;
import com.brewed.core.rest.beerratings.BeerRatingsRepository;
import com.brewed.core.rest.beertype.BeerTypeRepository;
import com.brewed.core.rest.brewery.BreweryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeerServiceTest {

    private static final Beer SAMPLE_BEER = BeerTestUtils.generateSampleBeer();
    private final BeerMapper mapper = new BeerMapper();
    @Mock
    private BeerRepository beerRepository;
    @Mock
    private BeerRatingsRepository beerRatingsRepository;
    @Mock
    private BreweryRepository breweryRepository;
    @Mock
    private BeerNoteRepository beerNoteRepository;

    @InjectMocks
    private BeerService beerService;
    @Mock
    private BeerTypeRepository beerTypeRepository;

    @Test
    void shouldGetValidBeerIfCorrectIdProvided() {
        when(beerRepository.findById(SAMPLE_BEER.getId())).thenReturn(Optional.of(SAMPLE_BEER));
        beerService.findById(SAMPLE_BEER.getId());
        verify(beerRepository).findById(SAMPLE_BEER.getId());
    }


    @Test
    void shouldReturnEmptyOptionalIfEntityNotFound() {
        UUID idNotPresent = UUID.randomUUID();
        lenient().when(beerRepository.getOne(idNotPresent))
                 .thenReturn(null);
        Optional<Beer> beerOptional = beerService.findById(idNotPresent);
        assertThat(beerOptional).isEmpty();
    }

    @Test
    void shouldReturnBeerByItsBarCode() {
        String barCode = "42342341";
        beerService.findByBarCode(barCode);
        verify(beerRepository).findBeerByBarCode(barCode);
    }

    @Test
    void shouldGetAllMethodBeCalled() {
        beerService.getAllBeers();
        verify(beerRepository).findAll();
    }

    @Test
    void shouldRemoveAllRelatedObjectWhileDeletingAllBeers() {
        beerService.deleteAll();
        verify(beerRepository).deleteAll();
        verify(beerRatingsRepository).deleteAll();
    }

    @Test
    void shouldAddMethodCallRepoMethodWithGoodParams() {
        BeerDto beerDto = new BeerDto();
        beerDto.setBeerNoteId(UUID.randomUUID());
        beerDto.setBeerTypeId(UUID.randomUUID());
        beerDto.setBreweryId(UUID.randomUUID());
        BeerNote beerNote = new BeerNote("BeerDto note");
        beerNote.setId(UUID.randomUUID());
        BeerType beerType = new BeerType("BeerDto type");
        beerType.setId(UUID.randomUUID());
        Brewery brewery = new Brewery();
        brewery.setId(UUID.randomUUID());
        brewery.setName("Brewery");
        brewery.setAddress("Address");
        brewery.setDescription("Description");
        when(beerNoteRepository.findById(beerDto.getBeerNoteId())).thenReturn(Optional.of(beerNote));
        when(beerTypeRepository.findById(beerDto.getBeerTypeId())).thenReturn(Optional.of(beerType));
        when(breweryRepository.findById(beerDto.getBreweryId())).thenReturn(Optional.of(brewery));
        beerService.addBeer(beerDto);
        Beer domain = mapper.toDomain(beerDto);
        domain.setBeerType(beerType);
        domain.setBeerNote(beerNote);
        domain.setBrewery(brewery);
        verify(beerRepository).save(domain);
    }

    @Test
    void shouldUpdateMethodCallCorrectMethodInRepositoryWithGoodParams() {
        BeerDto beer = new BeerDto();
        beer.setId(UUID.randomUUID());
        UUID beerNoteId = UUID.randomUUID();
        BeerNote testBeerNote = new BeerNote(beerNoteId, "Test Beer Note");
        beer.setBeerNoteId(beerNoteId);
        UUID beerTypeId = UUID.randomUUID();
        BeerType beerType = new BeerType(beerTypeId, "TestBeerType");
        beer.setBeerTypeId(beerTypeId);
        UUID breweryId = UUID.randomUUID();
        beer.setBreweryId(breweryId);
        Brewery brewery = new Brewery(breweryId, "BreweryName", "BreweryAddress", "BreweryDescription");
        when(beerRepository.findById(beer.getId())).thenReturn(Optional.of(mapper.toDomain(beer)));
        when(breweryRepository.findById(breweryId)).thenReturn(Optional.of(brewery));
        when(beerTypeRepository.findById(beerTypeId)).thenReturn(Optional.of(beerType));
        when(beerNoteRepository.findById(beerNoteId)).thenReturn(Optional.of(testBeerNote));
        beerService.updateBeer(beer);
        Beer entity = mapper.toDomain(beer);
        entity.setBeerNote(testBeerNote);
        entity.setBeerType(beerType);
        entity.setBrewery(brewery);
        verify(beerRepository).save(entity);
    }

    @Test
    void shouldRemoveMethodCallCorrectMethodInRepositoryWithGoodParams() {
        UUID id = UUID.randomUUID();
        beerService.delete(id);
        verify(beerRepository).deleteById(id);
    }

    @Test
    void shouldAddAllProvidedBeers() {
        List<Beer> beers = BeerTestUtils.generateRandomBeers(3);
        beerService.addProvidedBeers(beers);
        verify(beerRepository).saveAll(beers);
    }
}
