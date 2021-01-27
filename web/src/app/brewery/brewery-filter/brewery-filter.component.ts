import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-brewery-filter',
  templateUrl: './brewery-filter.component.html',
  styleUrls: ['./brewery-filter.component.css']
})
export class BreweryFilterComponent implements OnInit {
  breweryForm: FormGroup;
  @Output()
  public eventEmitter = new EventEmitter();

  constructor() {
  }

  ngOnInit(): void {
    this.breweryForm = this.createFormGroup();
  }

  onSearchClick() {
    this.eventEmitter.emit(this.breweryForm);
  }

  createFormGroup() {
    return new FormGroup({
      breweryName: new FormControl()
    });
  }
}
