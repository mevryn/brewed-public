import {Injectable} from '@angular/core';
import {BreweryService} from '../../services/brewery.service';
import {BeerNoteService} from '../../services/beer-note.service';
import {BeerService} from '../../services/beer.service';
import {BeerTypeService} from '../../services/beer-type.service';
import {Observable} from 'rxjs';
import {BeerNote} from '../../model/BeerNote';
import {BeerType} from '../../model/BeerType';
import {Brewery} from '../../model/Brewery';

@Injectable({
  providedIn: 'root'
})
export class BeerDialogService {

  constructor(private beerService: BeerService, private breweryService: BreweryService, private beerNoteService: BeerNoteService,
              private beerTypeService: BeerTypeService) {
  }


  getBrewery(breweryId: string): Observable<Brewery> {
    return this.breweryService.getBrewery(breweryId);
  }

  getNote(noteId: string): Observable<BeerNote> {
    return this.beerNoteService.getNote(noteId);
  }

  getType(typeId: string): Observable<BeerType> {
    return this.beerTypeService.getType(typeId);
  }

}
