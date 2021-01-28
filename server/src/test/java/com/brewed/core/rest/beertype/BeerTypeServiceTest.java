package com.brewed.core.rest.beertype;

import com.brewed.core.model.ModificationEnvelope;
import com.brewed.core.model.beer.BeerType;
import com.brewed.core.rest.beer.BeerRepository;
import com.brewed.core.rest.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BeerTypeServiceTest {


    @Mock
    private BeerTypeRepository beerTypeRepository;

    @Mock
    private BeerRepository beerRepository;
    @InjectMocks
    private BeerTypeService service;


    @Test
    void shouldGetByName() {
        String beerTypeName = "Sample beer type";
        service.getBeerType(beerTypeName);
        verify(beerTypeRepository).findByName(beerTypeName);
    }


    @Test
    void shouldCreate() {
        String beerTypeName = "Sample beer type";
        service.create(beerTypeName);
        verify(beerTypeRepository).save(new BeerType(beerTypeName));
    }

    @Test
    void shouldRemove() {

        UUID id = UUID.randomUUID();
        service.delete(id);
        verify(beerTypeRepository).deleteById(id);
    }

    @Test
    void shouldUpdateBeer() {
        String beerTypeName = "Sample beer type";
        String modifyBeerType = "Modified beer type";
        UUID uuid = UUID.randomUUID();
        ModificationEnvelope envelope = new ModificationEnvelope(beerTypeName, modifyBeerType);
        BeerType type = new BeerType(uuid);
        when(beerTypeRepository.findByName(envelope.current))
                .thenReturn(Optional.of(type));
        service.modifyBeerType(envelope);
        verify(beerTypeRepository).deleteById(uuid);
        verify(beerTypeRepository).save(new BeerType(modifyBeerType));
    }

    @Test
    void shouldThrowBeerTypeNotPresentWhileUpdating() {
        String beerTypeName = "Sample beer type";
        ModificationEnvelope modificationEnvelope = new ModificationEnvelope(beerTypeName, "");
        when(beerTypeRepository.findByName(modificationEnvelope.current))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.modifyBeerType(modificationEnvelope));
    }
}
