import {Beer} from '../model/Beer';
import {BeerDto} from '../model/BeerDto';

export class BeerMapper {
  public static toDto(beer: Beer) {
    let beerDto = new BeerDto();
    beerDto.name = beer.name;
    beerDto.breweryId = beer.brewery.id;
    beerDto.price = beer.price;
    beerDto.servingTemperature = beer.servingTemperature;
    beerDto.beerNoteId = beer.beerNote.id;
    beerDto.beerTypeId = beer.beerType.id;
    beerDto.alcoholPercentage = beer.alcoholPercentage;
    beerDto.ibu = beer.ibu;
    beerDto.barCode = beer.barCode;
    beerDto.id = beer.id;
    return beerDto;
  }
}
