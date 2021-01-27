import {Injectable} from '@angular/core';
import {SearchEnvelope, SearchEnvelopeFactory} from './SearchEnvelope';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {FormGroup} from '@angular/forms';
import {BeerDto} from '../model/BeerDto';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) {
  }

  beerUrl = '/beer';
  getBeersByAttrsUrl = '/beer/findByAttrs';

  search(beerForm: FormGroup): Observable<BeerDto[]> {
    const envelope = this.parseFormGroupIntoEnvelope(beerForm);
    if (envelope.isblank()) {
      return this.getIntermarcheBeers();
    } else {
      return this.getIntermarcheBeersByAttrs(envelope);
    }
  }


  parseFormGroupIntoEnvelope(beerForm: FormGroup): SearchEnvelope {
    return SearchEnvelopeFactory.create(beerForm);
  }

  private getIntermarcheBeers(): Observable<BeerDto[]> {
    return this.http.get<BeerDto[]>(environment.serverUrl + this.beerUrl);
  }

  private getIntermarcheBeersByAttrs(envelope: SearchEnvelope): Observable<BeerDto[]> {
    return this.http.post<BeerDto[]>(environment.serverUrl + this.getBeersByAttrsUrl, envelope);
  }
}
