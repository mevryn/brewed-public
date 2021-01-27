import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BeerModificationDialogComponent} from './beer-modification-dialog.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule} from '@angular/forms';
import {MatDialogModule} from '@angular/material/dialog';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {SharingModule} from '../../../sharing/sharing.module';
import {NgxCurrencyModule} from 'ngx-currency';
import {FxPercentageModule} from 'fx-percentage';


@NgModule({
  declarations: [BeerModificationDialogComponent],
  imports: [
    CommonModule,
    MatFormFieldModule,
    FormsModule,
    MatDialogModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    SharingModule,
    NgxCurrencyModule,
    FxPercentageModule
  ]
})
export class BeerModificationDialogModule {
}
