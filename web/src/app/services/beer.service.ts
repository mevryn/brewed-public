import {Injectable} from '@angular/core';
import {BeerDto} from '../model/BeerDto';
import {environment} from '../../environments/environment';
import {HttpClient, HttpParams} from '@angular/common/http';
import {AppService} from '../app.service';
import {Observable} from 'rxjs';
import {BeerFilterDto} from '../beer/beer-filter/beer-filter-dto';
import {BeerFilterFormFields} from '../beer/beer-filter/beer-filter-form-fields';
import {Beer} from '../model/Beer';

@Injectable({
  providedIn: 'root'
})
export class BeerService {

  private beerUrl = '/beer';
  private v2Url = '/v2';
  private filterUrl = '/filter';
  private addBeerUrl = '/beer/add';
  private deleteBeersUrl = '/beer/delete/selected';
  private deleteBeerUrl = '/beer/delete/';

  constructor(private http: HttpClient, private appService: AppService) {
  }

  modifyBeer(beerDto: BeerDto) {
    return this.http.put<BeerDto>(environment.serverUrl + this.beerUrl, beerDto, this.appService.authenticatedHeaders);
  }

  getAllBeersLite() {
    return this.http.get<BeerDto[]>(environment.serverUrl + this.beerUrl + this.v2Url);
  }

  getAllBeers() {
    return this.http.get<Beer[]>(environment.serverUrl + this.beerUrl);
  }

  addBeer(beer: BeerDto): Observable<BeerDto> {
    return this.http.post<BeerDto>(environment.serverUrl + this.addBeerUrl, beer);
  }

  deleteBeers(rows: any[]) {
    return this.http.post(environment.serverUrl + this.deleteBeersUrl, rows, {headers: this.appService.authenticatedHeaders});
  }


  deleteBeer(element: BeerDto) {
    return this.http.delete(environment.serverUrl + this.deleteBeerUrl + element.id, {headers: this.appService.authenticatedHeaders});
  }

  getFilteredBeers(beerFilter: BeerFilterDto) {
    let httpParams = new HttpParams();
    if (beerFilter.name != undefined) {
      httpParams = httpParams.set(BeerFilterFormFields.NAME, beerFilter.name);
    }
    if (beerFilter.breweryName != undefined) {
      httpParams = httpParams.set(BeerFilterFormFields.BREWERY_NAME, beerFilter.breweryName);
    }
    if (beerFilter.barCode != undefined) {
      httpParams = httpParams.set(BeerFilterFormFields.BAR_CODE, beerFilter.barCode);
    }
    return this.http.get<Beer[]>(environment.serverUrl + this.beerUrl + this.filterUrl, {
      params: httpParams,
      headers: this.appService.authenticatedHeaders
    });
  }
}
