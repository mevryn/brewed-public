import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Brewery} from '../model/Brewery';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {AppService} from '../app.service';
import {BreweryFilterFormFields} from '../brewery/brewery-filter/brewery-filter-form-fields';

@Injectable({
  providedIn: 'root'
})
export class BreweryService {

  breweryUrl = '/brewery';
  private deleteGivenBreweriesUrl = '/deleteGiven';
  private reportUrl: string = '/report';
  private filterUrl: string = '/filter';

  constructor(private appService: AppService, private http: HttpClient) {

  }

  getAllBreweries(): Observable<Brewery[]> {
    return this.http.get<Brewery[]>(environment.serverUrl + this.breweryUrl);
  }

  getBrewery(id: string): Observable<Brewery> {
    return this.http.get<Brewery>(environment.serverUrl + this.breweryUrl + '/' + id);
  }

  postBrewery(brewery: Brewery) {
    return this.http.post(environment.serverUrl + this.breweryUrl, brewery, this.appService.authenticatedHeaders);
  }
  deleteBreweries(rows: Brewery[]) {
    return this.http.post(environment.serverUrl + this.breweryUrl + this.deleteGivenBreweriesUrl, rows, this.appService.authenticatedHeaders);
  }

  delete(element) {
    return this.http.delete(environment.serverUrl + this.breweryUrl + '/' + element.id, this.appService.authenticatedHeaders);
  }

  generateReport(element: Brewery) {
    let httpParams = new HttpParams().set('breweryId', element.id);
    return this.http.get(environment.serverUrl + this.breweryUrl + this.reportUrl, {
      headers: this.appService.authenticatedHeaders,
      params: httpParams
    });
  }

  getFilteredBreweries(breweryFilter: any) {
    let httpParams = new HttpParams();
    if (breweryFilter.breweryName != undefined) {
      httpParams = httpParams.set(BreweryFilterFormFields.BREWERY_NAME, breweryFilter.breweryName);
    }
    if (breweryFilter.breweryName != '') {
      return this.http.get<Brewery[]>(environment.serverUrl + this.breweryUrl + this.filterUrl, {
        params: httpParams,
        headers: this.appService.authenticatedHeaders
      });
    } else {
      return this.getAllBreweries();
    }
  }
}
