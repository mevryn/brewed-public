import {Injectable} from '@angular/core';
import {BeerService} from '../../../services/beer.service';
import {BeerTypeService} from '../../../services/beer-type.service';
import {BeerNoteService} from '../../../services/beer-note.service';
import {BreweryService} from '../../../services/brewery.service';

@Injectable({
  providedIn: 'root'
})
export class AdditionDialogService {

  constructor(public beerService: BeerService, public beerTypeService: BeerTypeService,
              public beerNoteService: BeerNoteService, public breweryService: BreweryService) {
  }


}
