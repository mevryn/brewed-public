import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BreweryDialogComponent} from './brewery-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';


@NgModule({
  declarations: [BreweryDialogComponent],
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatDialogModule,
    FormsModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
  ]
})
export class BreweryDialogModule {
}
