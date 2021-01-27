import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BeerTableComponent} from './beer-table.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatTableModule} from '@angular/material/table';
import {BeerDialogModule} from '../beer-dialog/beer-dialog.module';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';


@NgModule({
  declarations: [BeerTableComponent],
  exports: [
    BeerTableComponent
  ],
  imports: [
    BeerDialogModule,
    CommonModule,
    MatPaginatorModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule
  ]
})
export class BeerTableModule {
}
