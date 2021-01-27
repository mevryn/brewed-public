import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BreweryAdministrationComponent} from './brewery-administration.component';
import {BreweryListModule} from '../../brewery/brewery-list/brewery-list.module';
import {BreweryModule} from '../../brewery/brewery.module';


@NgModule({
  declarations: [BreweryAdministrationComponent],
  exports: [
    BreweryAdministrationComponent
  ],
  imports: [
    CommonModule,
    BreweryListModule,
    BreweryModule
  ]
})
export class BreweryAdministrationModule {
}
