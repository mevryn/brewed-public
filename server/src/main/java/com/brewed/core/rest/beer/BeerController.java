package com.brewed.core.rest.beer;

import com.brewed.api.FilterDescription;
import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.beer.BeerDto;
import com.brewed.core.model.beer.BeerMapper;
import com.brewed.core.rest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@CrossOrigin
@RestController
@RequestMapping("/api/beer")
public class BeerController {

    private final BeerService beerService;
    private final BeerMapper beerMapper = new BeerMapper();

    @Autowired
    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{id}")
    public Beer getBeer(@PathVariable("id") String id) {
        Optional<Beer> beerOptional = beerService.findById(UUID.fromString(id));
        return beerOptional.orElseThrow(NotFoundException::new);
    }

    @GetMapping("/v2/{id}")
    public BeerDto getBeerV2(@PathVariable("id") String id) {
        return beerService.findByIdv2(UUID.fromString(id));
    }

    @GetMapping("/v2")
    public Collection<BeerDto> getBeersV2(@RequestParam(required = false, name = "name") String name,
                                          @RequestParam(required = false, name = "barCode") String barCode) {
        Collection<Beer> beers = getBeers(name, barCode);
        return beers.stream()
                    .map(beerMapper::toDto)
                    .collect(Collectors.toList());
    }

    @PostMapping(path = "/filter")
    public List<Beer> filter(@RequestBody FilterDescription description) {
        return beerService.filterBeers(description);
    }

    @GetMapping("/filter")
    public Collection<Beer> getFilteredBeers(@RequestParam(required = false, name = "barCode") String barCode,
                                             @RequestParam(required = false, name = "name") String name,
                                             @RequestParam(required = false, name = "breweryName") String breweryName) {
        return beerService.findFilteredBeers(barCode, name, breweryName);
    }

    @GetMapping
    public Collection<Beer> getBeers(@RequestParam(required = false, name = "name") String name,
                                     @RequestParam(required = false, name = "barCode") String barCode) {
        boolean barCodePresent = !(isNull(barCode) || barCode.isEmpty());
        boolean beerNamePresent = !(isNull(name) || name.isEmpty());
        if (barCodePresent) {
            return beerService.findByBarCode(barCode);
        } else if (beerNamePresent) {
            return beerService.findByName(name);
        } else {
            return beerService.getAllBeers();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Beer> addBeer(@RequestBody BeerDto dto) {
        return new ResponseEntity<>(beerService.addBeer(dto), HttpStatus.CREATED);
    }

    @PostMapping("/addAll")
    public ResponseEntity<List<Beer>> addAllProvided(@RequestBody Collection<Beer> beers) {
        return new ResponseEntity<>(beerService.addProvidedBeers(beers), HttpStatus.CREATED);
    }


    @PutMapping("/import")
    public ResponseEntity<List<Beer>> importWithCSV(@RequestParam("data") MultipartFile file,
                                                    @RequestParam(name = "attribute", required = false) String attribute) throws IOException {
        if (isNull(attribute) || attribute.isEmpty()) {
            return new ResponseEntity<>(beerService.importBeers(file), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(beerService.updateBeersFromCSV(file, attribute), HttpStatus.OK);
        }
    }


    @PostMapping("/import/combined")
    public ResponseEntity<List<Beer>> mergeCSV(@RequestParam("fullData") MultipartFile fullData,
                                               @RequestParam("filteredData") MultipartFile filteredData) throws IOException {
        return new ResponseEntity<>(beerService.importBeers(fullData, filteredData), HttpStatus.CREATED);
    }

    @PostMapping("/import")
    public ResponseEntity<List<Beer>> importBeers(@RequestParam("data") MultipartFile fullData) throws IOException {
        return new ResponseEntity<>(beerService.importBeers(fullData), HttpStatus.CREATED);
    }


    @PutMapping
    public Beer updateBeer(@RequestBody BeerDto dto) {
        return beerService.updateBeer(dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBeer(@PathVariable("id") UUID id) {
        beerService.delete(id);
    }

    @PostMapping("/delete/selected")
    public void deleteAllBeers(@RequestBody Collection<Beer> beers) {
        beerService.deleteAll(beers);
    }

    @PostMapping("/delete/all")
    public void deleteAllBeers() {
        beerService.deleteAll();
    }

    @PostMapping("/findByAttrs")
    public List<Beer> findAllBeersThatMatchEnvelopeContent(@RequestBody BeerRequestEnvelope beer) {
        return beerService.findAllBeersThatMatchEnvelopeContent(beer);
    }
}
