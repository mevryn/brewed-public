import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BeerAdministrationComponent} from './beer-administration.component';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {BeerTableModule} from '../../beer/beer-table/beer-table.module';
import {BeerModule} from '../../beer/beer.module';


@NgModule({
  declarations: [BeerAdministrationComponent],
  exports: [
    BeerAdministrationComponent
  ],
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    BeerTableModule,
    BeerModule
  ]
})
export class BeerAdministrationModule {
}
