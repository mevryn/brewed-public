import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BeerNoteListComponent} from './beer-note-list.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatTableModule} from '@angular/material/table';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';


@NgModule({
  declarations: [BeerNoteListComponent],
  exports: [
    BeerNoteListComponent
  ],
  imports: [
    CommonModule,
    MatPaginatorModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule
  ]
})
export class BeerNoteListModule {
}
