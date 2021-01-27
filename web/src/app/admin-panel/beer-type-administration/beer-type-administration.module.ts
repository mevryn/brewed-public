import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BeerTypeAdministrationComponent} from './beer-type-administration.component';
import {BeerTypeListModule} from '../../beer-type/beer-type-list/beer-type-list.module';


@NgModule({
  declarations: [BeerTypeAdministrationComponent],
  exports: [
    BeerTypeAdministrationComponent
  ],
  imports: [
    CommonModule,
    BeerTypeListModule
  ]
})
export class BeerTypeAdministrationModule {
}
