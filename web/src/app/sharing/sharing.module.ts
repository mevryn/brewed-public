import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {InformationDialogFactory} from './information-dialog-factory';
import {ConfimationDialogComponent} from './confimation-dialog/confimation-dialog.component';
import {MatButtonModule} from '@angular/material/button';
import {MatDialogModule} from '@angular/material/dialog';
import {BeerMapper} from './beer-mapper';


@NgModule({
  declarations: [ConfimationDialogComponent],
  imports: [
    CommonModule,
    MatButtonModule,
    MatDialogModule
  ], exports: [],
  providers: [InformationDialogFactory, BeerMapper]
})
export class SharingModule {
}
