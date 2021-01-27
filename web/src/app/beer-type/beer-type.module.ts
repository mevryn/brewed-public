import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BeerTypeListModule} from './beer-type-list/beer-type-list.module';
import {MatDialogModule} from '@angular/material/dialog';


@NgModule({
  declarations: [],
  imports: [
    BeerTypeListModule,
    CommonModule,
    MatDialogModule
  ]
})
export class BeerTypeModule {
}
