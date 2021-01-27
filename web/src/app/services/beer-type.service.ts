import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {BeerType} from '../model/BeerType';
import {Observable} from 'rxjs';
import {AppService} from '../app.service';

@Injectable({
  providedIn: 'root'
})
export class BeerTypeService {

  beerTypeUrl = '/beer-type';
  getAllUrl = '/all';
  deleteGivenUrl = '/deleteGiven';

  constructor(private appService: AppService, private http: HttpClient) {
  }

  getType(typeId: string): Observable<BeerType> {
    return this.http.get<BeerType>(environment.serverUrl + this.beerTypeUrl + '/' + typeId);
  }

  getTypeByName(beerType: string): Observable<BeerType> {
    let httpParams = new HttpParams();
    httpParams.append('name', beerType);
    return this.http.get<BeerType>(environment.serverUrl + this.beerTypeUrl + '?' + httpParams.toString());
  }

  getAllTypes(): Observable<BeerType[]> {
    return this.http.get<BeerType[]>(environment.serverUrl + this.beerTypeUrl + this.getAllUrl);
  }

  postType(entryData: BeerType) {
    return this.http.post(environment.serverUrl + this.beerTypeUrl, entryData, this.appService.authenticatedHeaders);
  }

  deleteTypes(rows: any[]) {
    return this.http.post(environment.serverUrl + this.beerTypeUrl + this.deleteGivenUrl, rows, this.appService.authenticatedHeaders);
  }

  delete(element) {
    return this.http.delete(environment.serverUrl + this.beerTypeUrl + '/' + element.id, this.appService.authenticatedHeaders);
  }
}
