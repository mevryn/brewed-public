import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdditionDialogComponent} from './addition-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatTabsModule} from '@angular/material/tabs';
import {MatIconModule} from '@angular/material/icon';
import {ColorPickerModule} from 'ngx-color-picker';
import {SharingModule} from '../../../sharing/sharing.module';
import {NgxCurrencyModule} from 'ngx-currency';
import {FxPercentageModule} from 'fx-percentage';


@NgModule({
  declarations: [AdditionDialogComponent],
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatTabsModule,
    FormsModule,
    MatIconModule,
    ColorPickerModule,
    SharingModule,
    NgxCurrencyModule,
    FxPercentageModule
  ],
  providers: []
})
export class AdditionDialogModule {
}
