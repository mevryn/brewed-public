import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BreweryInfoDialogComponent} from './brewery-info-dialog/brewery-info-dialog.component';
import {MatExpansionModule} from '@angular/material/expansion';
import {BreweryListModule} from './brewery-list/brewery-list.module';
import {BreweryFilterComponent} from './brewery-filter/brewery-filter.component';
import {MatIconModule} from '@angular/material/icon';
import {ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';


@NgModule({
  declarations: [BreweryInfoDialogComponent, BreweryFilterComponent],
  exports: [
    BreweryFilterComponent
  ],
  imports: [
    CommonModule,
    MatExpansionModule,
    BreweryListModule,
    MatIconModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule
  ]
})
export class BreweryModule {
}
