import {Component, EventEmitter, Inject, OnInit} from '@angular/core';
import {InformationDialogFactory} from '../../sharing/information-dialog-factory';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {BeerRatingsService} from '../beer-ratings.service';
import {BeerRatingDto} from '../beer-rating-dto';
import {BeerService} from '../../services/beer.service';
import {Severity} from '../../sharing/utilities/InformationDialogUtils';
import {BeerDto} from '../../model/BeerDto';

@Component({
  selector: 'app-rating-input',
  templateUrl: './rating-input.component.html',
  styleUrls: ['./rating-input.component.css']
})
export class RatingInputComponent implements OnInit {

  public eventEmitter = new EventEmitter();
  beers: BeerDto[];
  dialogType: boolean;

  constructor(private dialogFactory: InformationDialogFactory, private beerService: BeerService, private ratingService: BeerRatingsService,
              @Inject(MAT_DIALOG_DATA) public  data: { entryData: BeerRatingDto },
              public dialogRef: MatDialogRef<RatingInputComponent>) {
  }

  formatPercentLabel(value: number) {
    return Math.round(value * 100) + '%';
  }

  formatScoreLabel(value: number) {
    return value * 5;
  }

  ngOnInit(): void {
    this.dialogType = this.data.entryData.id != undefined;
    if (this.data.entryData.beer == undefined) {
      this.data.entryData.beer = -1;
    }
    console.log(this.data.entryData);
    this.beerService.getAllBeersLite()
      .subscribe(value => this.beers = value);
  }

  provideTitle() {
    return this.data.entryData.id != undefined ? 'Modyfikuj ocenę' : 'Dodaj ocenę';
  }

  informationDialogTextProvider() {
    return this.dialogType ? 'zmodyfikowana' : 'dodana';
  }

  onNoClick() {
    this.dialogRef.close();
  }

  apply() {
    this.ratingService.postRating(this.data.entryData).subscribe(
      value => {
        this.eventEmitter.emit();
        this.dialogFactory.openInformationDialog(Severity.INFORMATION, 'Ocena została ' + this.informationDialogTextProvider() + ' pomyślnie');
      }, error =>
        this.dialogFactory.openInformationDialog(Severity.ERROR, error.message)
    );
    this.dialogRef.close(this.data.entryData);
  }

  isInvalidBeerSelected(beerId): boolean {
    return beerId == -1;
  }

}
