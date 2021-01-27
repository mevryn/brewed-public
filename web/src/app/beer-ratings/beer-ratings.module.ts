import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BeerRatingsComponent} from './beer-ratings.component';
import {RatingListComponent} from './rating-list/rating-list.component';
import {RatingFilterComponent} from './rating-filter/rating-filter.component';
import {RatingInputComponent} from './rating-input/rating-input.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {FilterDtoMapper} from './rating-filter/filter-dto-mapper';
import {BeerRatingsService} from './beer-ratings.service';
import {MatTableModule} from '@angular/material/table';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatExpansionModule} from '@angular/material/expansion';
import {RatingsInfoDialogComponent} from './ratings-info-dialog/ratings-info-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatSliderModule} from '@angular/material/slider';


@NgModule({
  declarations: [BeerRatingsComponent, RatingListComponent, RatingFilterComponent, RatingInputComponent, RatingsInfoDialogComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatPaginatorModule,
    MatExpansionModule,
    MatDialogModule,
    FormsModule,
    MatSliderModule
  ],
  providers: [FilterDtoMapper, BeerRatingsService]
})
export class BeerRatingsModule {
}
