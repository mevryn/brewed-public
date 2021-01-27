import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BeerDto} from '../model/BeerDto';
import {AppService} from '../app.service';
import {ModelService} from '../services/model.service';

@Injectable({
  providedIn: 'root'
})
export class AdminPanelService {

  constructor(private http: HttpClient, private modelService: ModelService, private app: AppService) {
  }


  modifyBeer(beer: BeerDto) {
    return this.modelService.beerService.modifyBeer(beer);
  }

  deleteBreweries(rows: any[]) {
    return this.modelService.breweryService.deleteBreweries(rows);
  }

  deleteNotes(rows: any[]) {
    return this.modelService.beerNoteService.deleteNotes(rows);
  }

  deleteTypes(rows: any[]) {
    return this.modelService.beerTypeService.deleteTypes(rows);

  }
}
