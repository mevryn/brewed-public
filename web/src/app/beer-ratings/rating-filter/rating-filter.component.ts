import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {HttpClient} from '@angular/common/http';
import {SearchService} from '../../search/search.service';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-rating-filter',
  templateUrl: './rating-filter.component.html',
  styleUrls: ['./rating-filter.component.css']
})
export class RatingFilterComponent implements OnInit {


  @Output()
  filterEvent = new EventEmitter<any>();
  filterForm: FormGroup;

  constructor(private dialog: MatDialog, private http: HttpClient, private searchService: SearchService) {
    this.filterForm = this.createFormGroup();
  }

  ngOnInit(): void {
  }

  createFormGroup() {
    return new FormGroup({
      userIdentifier: new FormControl(),
      beerName: new FormControl()
    });
  }

  applyFilter(event) {
    this.filterEvent.emit(this.filterForm);
  }
}
