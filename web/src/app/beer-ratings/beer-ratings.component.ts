import {Component, OnInit, ViewChild} from '@angular/core';
import {RatingListComponent} from './rating-list/rating-list.component';
import {FormGroup} from '@angular/forms';
import {FilterDtoMapper} from './rating-filter/filter-dto-mapper';

@Component({
  selector: 'app-beer-ratings',
  templateUrl: './beer-ratings.component.html',
  styleUrls: ['./beer-ratings.component.css']
})
export class BeerRatingsComponent implements OnInit {


  @ViewChild('list')
  list: RatingListComponent;

  constructor() {
  }

  ngOnInit(): void {
  }

  applyFilter(event: FormGroup) {
    this.list.refresh(FilterDtoMapper.toDto(event));
  }

}
