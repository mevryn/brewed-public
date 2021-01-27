import {Component, OnInit, ViewChild} from '@angular/core';
import {BreweryListComponent} from '../../brewery/brewery-list/brewery-list.component';

@Component({
  selector: 'app-brewery-administration',
  templateUrl: './brewery-administration.component.html',
  styleUrls: ['./brewery-administration.component.css']
})
export class BreweryAdministrationComponent implements OnInit {
  @ViewChild('table')
  public table: BreweryListComponent;

  constructor() {
  }

  ngOnInit(): void {
  }

}
