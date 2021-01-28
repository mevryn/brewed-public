package com.brewed.core.rest.beer;

import com.brewed.api.FilterDescription;
import com.brewed.core.model.beer.*;
import com.brewed.core.model.beercharatings.BeerRating;
import com.brewed.core.model.brewery.Brewery;
import com.brewed.core.model.brewery.BreweryConstants;
import com.brewed.core.rest.beer.importbeers.BeerBeanMapper;
import com.brewed.core.rest.beer.importbeers.ImportBearBean;
import com.brewed.core.rest.beernote.BeerNoteRepository;
import com.brewed.core.rest.beerratings.BeerRatingsRepository;
import com.brewed.core.rest.beertype.BeerTypeRepository;
import com.brewed.core.rest.brewery.BreweryRepository;
import com.brewed.core.rest.exception.NotFoundException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Transactional
@Service
public class BeerService {
    public static final String ALCOHOL_PERCENTAGE = "alcoholPercentage";
    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper = new BeerMapper();
    private final BreweryRepository breweryRepository;
    private final BeerRatingsRepository beerRatingsRepository;
    private final BeerNoteRepository beerNoteRepository;
    private final BeerTypeRepository beerTypeRepository;

    @Autowired
    public BeerService(BeerRepository beerRepository, BreweryRepository breweryRepository,
                       BeerRatingsRepository beerRatingsRepository, BeerNoteRepository beerNoteRepository,
                       BeerTypeRepository beerTypeRepository) {
        this.beerRepository = beerRepository;
        this.breweryRepository = breweryRepository;
        this.beerRatingsRepository = beerRatingsRepository;
        this.beerNoteRepository = beerNoteRepository;
        this.beerTypeRepository = beerTypeRepository;
    }

    public Optional<Beer> findById(UUID id) {
        return beerRepository.findById(id);
    }

    Collection<Beer> findByName(String name) {
        return beerRepository.findBeerByName(name);
    }


    Collection<Beer> getAllBeers() {
        return beerRepository.findAll();
    }


    public Beer addBeer(BeerDto beer) {
        //parse dto to domain
        Beer domainBeer = beerMapper.toDomain(beer);
        //fill beer's
        domainBeer.setBrewery(nonNull(beer.getBreweryId()) ?
                                      breweryRepository.findById(beer.getBreweryId())
                                                       .orElseThrow(() -> new NotFoundException("Brewery with given " +
                                                                                                        "id not found")) :
                                      BreweryConstants.DEFAULT_BREWERY);
        //fill beer's note
        domainBeer.setBeerNote(nonNull(beer.getBeerNoteId()) ?
                                       beerNoteRepository.findById(beer.getBeerNoteId())
                                                         .orElseThrow(
                                                                 () -> new NotFoundException("Beer Note note with " +
                                                                                                     "given" +
                                                                                                     " " +
                                                                                                     "id not " +
                                                                                                     "found")) :
                                       BeerNoteConstants.DEFAULT_BEER_NOTE);
        //fill beer's type
        domainBeer.setBeerType(nonNull(beer.getBeerTypeId()) ?
                                       beerTypeRepository.findById(beer.getBeerTypeId())
                                                         .orElseThrow(() -> new NotFoundException("Beer Type type " +
                                                                                                          "with " +
                                                                                                          "given" +
                                                                                                          "id is not " +
                                                                                                          "found")) :
                                       BeerTypeConstants.DEFAULT_BEER_TYPE);
        return beerRepository.save(domainBeer);
    }


    public Beer updateBeer(BeerDto beer) {
        Beer domainBeer = beerMapper.toDomain(beer);
        if (beerRepository.findById(domainBeer.getId())
                          .isEmpty()) {
            throw new NotFoundException();
        }
        domainBeer.setBeerType(beerTypeRepository.findById(beer.getBeerTypeId())
                                                 .orElseThrow(NotFoundException::new));
        domainBeer.setBrewery(breweryRepository.findById(beer.getBreweryId())
                                               .orElseThrow(NotFoundException::new));
        domainBeer.setBeerNote(beerNoteRepository.findById(beer.getBeerNoteId())
                                                 .orElseThrow(NotFoundException::new));
        return beerRepository.save(domainBeer);
    }

    void delete(UUID id) {
        List<BeerRating> beerRatings = beerRatingsRepository.getAllBeerRatings(id);
        beerRatingsRepository.deleteAll(beerRatings);
        beerRepository.deleteById(id);
    }

    public List<Beer> addProvidedBeers(Collection<Beer> beers) {
        return beerRepository.saveAll(new ArrayList<>(beers));
    }

    public List<Beer> getBeersByBarCode(String barCode) {
        return beerRepository.findBeerByBarCode(barCode);
    }

    public List<Beer> findAllBeersThatMatchEnvelopeContent(BeerRequestEnvelope beer) {
        return beerRepository.findAllBeersThatMatchEnvelopeContent(beer);
    }

    public void deleteAll(Collection<Beer> beers) {
        List<BeerRating> beerRatings = beers.stream()
                                            .flatMap(beer -> beerRatingsRepository.getAllBeerRatings(beer.getId())
                                                                                  .stream())
                                            .collect(toList());
        beerRatingsRepository.deleteAll(beerRatings);
        beerRepository.deleteAll(beers);
    }

    public List<Beer> importBeers(MultipartFile file) throws IOException {

        CsvToBean<ImportBearBean> csvToBean = processCSV(file);
        List<Beer> beers = csvToBean.parse()
                                    .stream()
                                    .map(BeerBeanMapper::toModel)
                                    .collect(toList());
        appendMissingTypesAndNotes(beers);
        appendMissingBreweries(beers);
        List<Beer> result = new ArrayList<>();
        for (Beer beer : beers) {
            result.add(addBeer(beerMapper.toDto(beer)));
        }
        return result;
    }

    private void appendMissingBreweries(List<Beer> beers) {
        addBreweriesToDatabase(beers);
        for (Beer beer : beers) {
            if (isNull(beer.getBrewery()) || beer.getBrewery()
                                                 .getName()
                                                 .isEmpty()) {
                beer.setBrewery(BreweryConstants.DEFAULT_BREWERY);
            } else {
                beer.setBrewery(breweryRepository.findByName(beer.getBrewery()
                                                                 .getName())
                                                 .orElseThrow());
            }
        }

    }

    private void addBreweriesToDatabase(List<Beer> beers) {
        Set<Brewery> notesFromCSV = beers.stream()
                                         .map(Beer::getBrewery)
                                         .collect(Collectors.toSet());
        List<Brewery> breweries = breweryRepository.findAll();
        Set<Brewery> filteredNotSavedBreweries = notesFromCSV.stream()
                                                             .filter(brewery -> !breweries.contains(brewery))
                                                             .filter(brewery -> !brewery.getName()
                                                                                        .equals(""))
                                                             .collect(Collectors.toSet());
        breweryRepository.saveAll(filteredNotSavedBreweries);
    }

    public List<Beer> importBeers(MultipartFile fullData, MultipartFile filteredData) throws IOException {

        List<Beer> filteredBeers = processCSV(filteredData).parse()
                                                           .stream()
                                                           .map(BeerBeanMapper::toModel)
                                                           .collect(toList());
        List<Beer> fullBeers = processCSV(fullData).parse()
                                                   .stream()
                                                   .map(BeerBeanMapper::toModel)
                                                   .collect(toList());


        List<Beer> filteredFullBeers = fullBeers.stream()
                                                .filter(beer -> filteredBeers.stream()
                                                                             .map(Beer::getBarCode)
                                                                             .collect(toList())
                                                                             .contains(beer.getBarCode()))
                                                .collect(toList());
        Map<String, Beer> eanFilteredFullBeerMap = produceBeerEanMap(filteredFullBeers);
        Map<String, Beer> eanFilteredBeerMap = produceBeerEanMap(filteredBeers);
        for (Map.Entry<String, Beer> entry : eanFilteredBeerMap.entrySet()) {
            entry.getValue()
                 .setPrice(eanFilteredFullBeerMap.get(entry.getKey())
                                                 .getPrice());
            entry.getValue()
                 .setBrewery(eanFilteredFullBeerMap.get(entry.getKey())
                                                   .getBrewery());
            entry.getValue()
                 .setServingTemperature(eanFilteredFullBeerMap.get(entry.getKey())
                                                              .getServingTemperature());

            entry.getValue()
                 .setIbu(eanFilteredFullBeerMap.get(entry.getKey())
                                               .getIbu());

        }
        List<Beer> values = new ArrayList<>(eanFilteredBeerMap.values());
        refactorBeersAndBreweriesNames(values);
        handleBreweries(values);
        appendMissingTypesAndNotes(values);
        return beerRepository.saveAll(values);
    }

    private Map<String, Beer> produceBeerEanMap(List<Beer> filteredFullBeers) {
        Map<String, Beer> eanFilteredFullBeerMap = new HashMap<>();
        for (Beer filteredFullBeer : filteredFullBeers) {
            eanFilteredFullBeerMap.put(filteredFullBeer.getBarCode(), filteredFullBeer);
        }
        return eanFilteredFullBeerMap;
    }

    private void appendMissingTypesAndNotes(List<Beer> beers) {
        addNotesToDatabase(beers);
        addTypesToDatabase(beers);
        for (Beer beer : beers) {
            if (isNull(beer.getBeerType()) || beer.getBeerType()
                                                  .getType()
                                                  .isEmpty()) {
                beer.setBeerType(BeerTypeConstants.DEFAULT_BEER_TYPE);
            } else {
                beer.setBeerType(beerTypeRepository.findByName(beer.getBeerType()
                                                                   .getType())
                                                   .orElseThrow());
            }
            if (isNull(beer.getBeerNote()) || beer.getBeerNote()
                                                  .getNote()
                                                  .isEmpty()) {
                beer.setBeerNote(BeerNoteConstants.DEFAULT_BEER_NOTE);
            } else {
                beer.setBeerNote(beerNoteRepository.findByNote(beer.getBeerNote()
                                                                   .getNote())
                                                   .orElseThrow());
            }
        }
    }

    private void addTypesToDatabase(List<Beer> beers) {
        Set<BeerType> typesFromCSV = beers.stream()
                                          .map(Beer::getBeerType)
                                          .collect(Collectors.toSet());
        List<BeerType> allTypes = beerTypeRepository.findAll();
        Set<BeerType> filteredNotSavedTypes = typesFromCSV.stream()
                                                          .filter(type -> !allTypes.contains(type))
                                                          .filter(type -> !type.getType()
                                                                               .equals(""))

                                                          .collect(Collectors.toSet());
        beerTypeRepository.saveAll(filteredNotSavedTypes);
    }

    private void addNotesToDatabase(List<Beer> beers) {
        Set<BeerNote> notesFromCSV = beers.stream()
                                          .map(Beer::getBeerNote)
                                          .collect(Collectors.toSet());
        List<BeerNote> allNotes = beerNoteRepository.findAll();
        Set<BeerNote> filteredNotSavedNotes = notesFromCSV.stream()
                                                          .filter(note -> !allNotes.contains(note))
                                                          .filter(note -> !note.getNote()
                                                                               .equals(""))
                                                          .collect(Collectors.toSet());
        beerNoteRepository.saveAll(filteredNotSavedNotes);
    }

    private CsvToBean<ImportBearBean> processCSV(MultipartFile file) throws IOException {
        HeaderColumnNameMappingStrategy<ImportBearBean> strategy
                = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(ImportBearBean.class);
        return new CsvToBeanBuilder<ImportBearBean>(
                new InputStreamReader(file.getInputStream()))
                .withMappingStrategy(strategy)
                .withSeparator(';')
                .withIgnoreLeadingWhiteSpace(true)
                .build();
    }

    private void handleBreweries(List<Beer> beers) {
        List<Brewery> breweries = breweryRepository.saveAll(beers.stream()
                                                                 .map(Beer::getBrewery)
                                                                 .collect(Collectors.toSet()));
        remapBreweries(beers, breweries);
    }

    private void remapBreweries(List<Beer> beers, List<Brewery> breweries) {
        Map<String, Brewery> breweryNameMap = new HashMap<>();
        breweries.forEach(brewery -> breweryNameMap.put(brewery.getName(), brewery));
        for (Beer beer : beers) {
            beer.setBrewery(breweryNameMap.get(beer.getBrewery()
                                                   .getName()));
        }
    }

    private void refactorBeersAndBreweriesNames(List<Beer> beers) {
        for (Beer beer : beers) {
            beer.setName(WordUtils.capitalize(beer.getName()
                                                  .toLowerCase(Locale.ROOT)));
        }
        beers.forEach(beer -> beer.getBrewery()
                                  .setName(WordUtils.capitalize(beer.getBrewery()
                                                                    .getName()
                                                                    .toLowerCase(Locale.ROOT))));
    }


    public void deleteAll() {
        beerRatingsRepository.deleteAll();
        beerRepository.deleteAll();
    }

    public List<Beer> findByBarCode(String barCode) {
        return beerRepository.findBeerByBarCode(barCode);
    }

    public List<Beer> updateBeersFromCSV(MultipartFile file, String attribute) throws IOException {
        CsvToBean<ImportBearBean> csvToBean = processCSV(file);
        List<Beer> beers = csvToBean.parse()
                                    .stream()
                                    .map(BeerBeanMapper::toModel)
                                    .collect(toList());
        List<Beer> beersToSave = new ArrayList<>();
        for (Beer beer : beers) {
            Beer beerFromDB = beerRepository.findBeerByBarCode(beer.getBarCode())
                                            .get(0);
            if (attribute.equals(ALCOHOL_PERCENTAGE)) {
                beerFromDB.setAlcoholPercentage(beer.getAlcoholPercentage());
                beersToSave.add(beerFromDB);
            }
        }
        return beerRepository.saveAll(beersToSave);
    }

    public List<Beer> filterBeers(FilterDescription description) {
        return beerRepository.filter(description);
    }

    public BeerDto findByIdv2(UUID id) {
        return beerMapper.toDto(beerRepository.findById(id)
                                              .orElseThrow(NotFoundException::new));
    }

    public Collection<Beer> findFilteredBeers(String barCode, String name, String breweryName) {
        BeerRequestEnvelope beerRequestEnvelope = new BeerRequestEnvelope();
        beerRequestEnvelope.setBreweryName(breweryName);
        beerRequestEnvelope.setName(name);
        beerRequestEnvelope.setBarCode(barCode);
        return beerRepository.findAllBeersThatMatchEnvelopeContent(beerRequestEnvelope);
    }
}
