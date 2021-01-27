import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {BeerRatingPdo} from '../beer-rating-pdo';
import {BeerRatingsService} from '../beer-ratings.service';
import {BeerRatingDto} from '../beer-rating-dto';

@Component({
  selector: 'app-ratings-info-dialog',
  templateUrl: './ratings-info-dialog.component.html',
  styleUrls: ['./ratings-info-dialog.component.css']
})
export class RatingsInfoDialogComponent implements OnInit {

  beerRatingDto: BeerRatingDto;

  constructor(public dialogRef: MatDialogRef<RatingsInfoDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: { entryData: BeerRatingPdo },
              private service: BeerRatingsService) {
  }

  ngOnInit(): void {
    this.service.getBeerRating(this.data.entryData).subscribe(value => this.beerRatingDto = value);
  }

}
