import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BreweryListComponent} from './brewery-list.component';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';


@NgModule({
  declarations: [BreweryListComponent],
  exports: [
    BreweryListComponent
  ],
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatIconModule,
    MatButtonModule
  ]
})
export class BreweryListModule {
}
