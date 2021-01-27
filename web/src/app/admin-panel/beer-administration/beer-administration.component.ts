import {Component, OnInit, ViewChild} from '@angular/core';
import {BeerTableComponent} from '../../beer/beer-table/beer-table.component';

@Component({
  selector: 'app-beer-administration',
  templateUrl: './beer-administration.component.html',
  styleUrls: ['./beer-administration.component.css']
})
export class BeerAdministrationComponent implements OnInit {


  @ViewChild('table')
  private table: BeerTableComponent;

  constructor() {
  }

  ngOnInit(): void {
  }

}
