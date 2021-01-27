import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BeerFilterComponent} from './beer-filter/beer-filter.component';
import {BeerTableModule} from './beer-table/beer-table.module';
import {BeerDialogModule} from './beer-dialog/beer-dialog.module';
import {BeerFilterDtoMapper} from './beer-filter/beer-filter-dto-mapper';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatIconModule} from '@angular/material/icon';
import {ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';


@NgModule({
  declarations: [BeerFilterComponent],
  imports: [
    CommonModule,
    BeerTableModule,
    BeerDialogModule,
    MatExpansionModule,
    MatIconModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule
  ], exports: [BeerFilterComponent], providers: [BeerFilterDtoMapper]
})
export class BeerModule {
}
