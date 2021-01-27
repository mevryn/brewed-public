import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BeerTypeListComponent} from './beer-type-list.component';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';


@NgModule({
  declarations: [BeerTypeListComponent],
  exports: [
    BeerTypeListComponent
  ],
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatButtonModule,
    MatIconModule
  ]
})
export class BeerTypeListModule {
}
