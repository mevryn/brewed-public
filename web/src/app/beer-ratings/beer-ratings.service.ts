import {EventEmitter, Injectable} from '@angular/core';
import {FilterDto} from './rating-filter/filter-dto';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {RatingFilterFormFields} from './rating-filter/rating-filter-form-fields';
import {AppService} from '../app.service';
import {Observable} from 'rxjs';
import {BeerRatingPdo} from './beer-rating-pdo';
import {MatDialog} from '@angular/material/dialog';
import {BeerRatingConstants} from './beer-rating-constants';
import {BeerRatingDto} from './beer-rating-dto';

@Injectable({
  providedIn: 'root'
})
export class BeerRatingsService {

  private ratingUrl = '/beer-ratings';
  private filterUrl = '/filter';

  public updatedContent = new EventEmitter();


  constructor(private dialog: MatDialog, private http: HttpClient, private appService: AppService) {
  }

  applyFilter(filterDto: FilterDto): Observable<BeerRatingPdo[]> {
    let httpParams = new HttpParams();
    if (filterDto.userIdentifier != undefined) {
      httpParams = httpParams.set(RatingFilterFormFields.USER_IDENTIFIER, filterDto.userIdentifier);
    }
    if (filterDto.beerName != undefined) {
      httpParams = httpParams.set(RatingFilterFormFields.BEER_NAME, filterDto.beerName);
    }
    return this.http.get<BeerRatingPdo[]>(environment.serverUrl + this.ratingUrl + this.filterUrl, {
      params: httpParams,
      headers: this.appService.authenticatedHeaders
    });
  }


  public getBeerRating(rating: BeerRatingPdo): Observable<BeerRatingDto> {
    let httpParams = new HttpParams().set(BeerRatingConstants.BEER_RATING_USER_ID, rating.userIdentifier)
      .set(BeerRatingConstants.BEER_RATING_BEER_ID, rating.beerId);
    return this.http.get<BeerRatingDto>(environment.serverUrl + this.ratingUrl, {
      params: httpParams, headers: this.appService.authenticatedHeaders
    });
  }

  deleteRating(element) {
    let httpParams = new HttpParams().set(BeerRatingConstants.BEER_RATING_USER_ID, element.userIdentifier)
      .set(BeerRatingConstants.BEER_RATING_BEER_ID, element.beerId);
    this.http
      .delete(environment.serverUrl + this.ratingUrl,
        {params: httpParams, headers: this.appService.authenticatedHeaders}).subscribe(
      () => this.updatedContent.emit());
  }

  postRating(entryData: BeerRatingDto) {
    return this.http.put(environment.serverUrl + this.ratingUrl,
      entryData,
      this.appService.authenticatedHeaders);
  }
}
