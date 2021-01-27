import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BeerDialogComponent} from './beer-dialog.component';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatCardModule} from '@angular/material/card';


@NgModule({
  declarations: [BeerDialogComponent],
  imports: [
    CommonModule,
    MatExpansionModule,
    MatCardModule,

  ]
})
export class BeerDialogModule {
}
