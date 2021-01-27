import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-beer-filter',
  templateUrl: './beer-filter.component.html',
  styleUrls: ['./beer-filter.component.css']
})
export class BeerFilterComponent implements OnInit {
  beerForm: FormGroup;
  @Output()
  public eventEmitter = new EventEmitter();

  constructor() {
  }

  ngOnInit(): void {
    this.beerForm = this.createFormGroup();
  }

  onSearchClick() {
    this.eventEmitter.emit(this.beerForm);
  }

  createFormGroup() {
    return new FormGroup({
      name: new FormControl(),
      breweryName: new FormControl(),
      barCode: new FormControl()
    });
  }
}
