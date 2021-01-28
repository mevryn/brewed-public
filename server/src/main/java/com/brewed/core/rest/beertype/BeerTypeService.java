package com.brewed.core.rest.beertype;

import com.brewed.core.model.ModificationEnvelope;
import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.beer.BeerType;
import com.brewed.core.model.beer.BeerTypeConstants;
import com.brewed.core.rest.beer.BeerRepository;
import com.brewed.core.rest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class BeerTypeService {

    private final BeerTypeRepository beerTypeRepository;
    private final BeerRepository beerRepository;

    @Autowired
    public BeerTypeService(BeerRepository beerRepository, BeerTypeRepository beerTypeRepository) {
        this.beerRepository = beerRepository;
        this.beerTypeRepository = beerTypeRepository;
    }

    public Optional<BeerType> getBeerType(String name) {
        return beerTypeRepository.findByName(name);
    }

    public Collection<BeerType> getAllBeerTypes() {
        return beerTypeRepository.findAll();
    }

    public Optional<BeerType> getBeerTypeById(String id) {
        return beerTypeRepository.findById(UUID.fromString(id));
    }

    public BeerType create(String type) {
        Optional<BeerType> byName = beerTypeRepository.findByName(type);
        return byName.orElseGet(() -> beerTypeRepository.save(new BeerType(type)));
    }

    public BeerType modifyBeerType(ModificationEnvelope envelope) {
        Optional<BeerType> byName = beerTypeRepository.findByName(envelope.current);
        if (byName.isEmpty()) {
            throw new NotFoundException();
        } else {
            beerTypeRepository.deleteById(byName.get()
                                                .getId());
            return beerTypeRepository.save(new BeerType(envelope.modified));
        }
    }

    public BeerType create(BeerType beerType) {
        return beerTypeRepository.saveAndFlush(beerType);
    }

    public void delete(UUID id) {
        if (id.equals(BeerTypeConstants.DEFAULT_BEER_TYPE.getId())) {
            throw new IllegalArgumentException("Default value can't be modified");
        }
        Collection<Beer> beerByBeerType = beerRepository.findBeersByBeerTypeId(id);
        for (Beer beer : beerByBeerType) {
            beer.setBeerType(BeerTypeConstants.DEFAULT_BEER_TYPE);
        }
        beerRepository.saveAll(beerByBeerType);
        beerTypeRepository.deleteById(id);
    }

    public void deleteAll(Collection<BeerType> beerTypes) {
        if (beerTypes.contains(BeerTypeConstants.DEFAULT_BEER_TYPE)) {
            throw new IllegalArgumentException("Default value can't be modified");
        }
        Collection<Beer> beerByBeerType = beerRepository.findBeerByBeerType(beerTypes);
        for (Beer beer : beerByBeerType) {
            beer.setBeerType(BeerTypeConstants.DEFAULT_BEER_TYPE);
        }
        beerRepository.saveAll(beerByBeerType);
        beerTypeRepository.deleteAll(beerTypes);
    }

    public BeerType updateBeerType(BeerType type) {
        if (type.equals(BeerTypeConstants.DEFAULT_BEER_TYPE)) {
            throw new IllegalArgumentException("Default value can't be modified");
        }
        return beerTypeRepository.save(type);
    }
}
