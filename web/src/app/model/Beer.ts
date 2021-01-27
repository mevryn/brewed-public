import {Brewery} from './Brewery';
import {BeerType} from './BeerType';
import {BeerNote} from './BeerNote';

export class Beer {
  barCode: string;
  name: string;
  brewery: Brewery;
  beerType: BeerType;
  price: number;
  alcoholPercentage: number;
  ibu: number;
  servingTemperature: number;
  beerNote: BeerNote;
  id: string;
}
