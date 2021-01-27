import {Injectable} from '@angular/core';
import {BeerService} from './beer.service';
import {BeerTypeService} from './beer-type.service';
import {BeerNoteService} from './beer-note.service';
import {BreweryService} from './brewery.service';

@Injectable({
  providedIn: 'root'
})
export class ModelService {

  constructor(public beerService: BeerService, public beerTypeService: BeerTypeService,
              public beerNoteService: BeerNoteService, public breweryService: BreweryService) {
  }
}
