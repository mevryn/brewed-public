package com.brewed.core.rest.beertype;


import com.brewed.core.model.ModificationEnvelope;
import com.brewed.core.model.beer.BeerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/beer-type")
public class BeerTypeController {

    private final BeerTypeService beerTypeService;

    @Autowired
    public BeerTypeController(BeerTypeService beerTypeService) {
        this.beerTypeService = beerTypeService;
    }

    @GetMapping
    public Optional<BeerType> getBeerType(@RequestParam(name = "name") String type) {
        return beerTypeService.getBeerType(type);
    }

    @GetMapping("/all")
    public Collection<BeerType> getAllBeerTypes() {
        return beerTypeService.getAllBeerTypes();
    }


    @GetMapping("/{id}")
    public Optional<BeerType> getBeerTypeById(@PathVariable("id") String id) {
        return beerTypeService.getBeerTypeById(id);
    }

    @PostMapping("/create")
    public BeerType create(@RequestBody String type) {
        return beerTypeService.create(type);
    }

    @PostMapping
    public BeerType updateBeerType(@RequestBody BeerType type) {
        return beerTypeService.updateBeerType(type);
    }

    @PutMapping
    public BeerType update(@RequestBody ModificationEnvelope envelope) {
        return beerTypeService.modifyBeerType(envelope);
    }

    @PostMapping("/deleteGiven")
    public void deleteAllBeers(@RequestBody Collection<BeerType> beerTypes) {
        beerTypeService.deleteAll(beerTypes);
    }

    @DeleteMapping("/{id}")
    public void deleteBeer(@PathVariable(name = "id") UUID id) {
        beerTypeService.delete(id);
    }
}
