import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdditionDialogModule} from './beer-administration/addition-dialog/addition-dialog.module';
import {AdminPanelComponent} from './admin-panel.component';
import {MatTabsModule} from '@angular/material/tabs';
import {MatButtonModule} from '@angular/material/button';
import {MatDialogModule} from '@angular/material/dialog';
import {BeerTableModule} from '../beer/beer-table/beer-table.module';
import {BeerTableComponent} from '../beer/beer-table/beer-table.component';
import {ImportComponent} from './import/import.component';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {BreweryDialogModule} from './brewery-administration/brewery-dialog/brewery-dialog.module';
import {BreweryListModule} from '../brewery/brewery-list/brewery-list.module';
import {BeerTypeDialogModule} from './beer-type-administration/beer-type-dialog/beer-type-dialog.module';
import {BeerNoteDialogModule} from './beer-note-administration/beer-note-dialog/beer-note-dialog.module';
import {BeerModificationDialogModule} from './beer-administration/beer-modification-dialog/beer-modification-dialog.module';
import {BeerAdministrationModule} from './beer-administration/beer-administration.module';
import {BreweryAdministrationModule} from './brewery-administration/brewery-administration.module';
import {BeerTypeAdministrationModule} from './beer-type-administration/beer-type-administration.module';
import {BeerNoteAdministrationModule} from './beer-note-administration/beer-note-administration.module';


@NgModule({
  declarations: [AdminPanelComponent,
    ImportComponent],
  imports: [
    CommonModule,
    AdditionDialogModule,
    MatTabsModule,
    MatButtonModule,
    MatDialogModule,
    BeerTableModule,
    BreweryDialogModule,
    BeerTypeDialogModule,
    BeerNoteDialogModule,
    BeerModificationDialogModule,
    MatCardModule,
    MatFormFieldModule,
    FormsModule,
    MatIconModule,
    MatInputModule,
    BreweryListModule,
    BeerAdministrationModule,
    BreweryAdministrationModule, BeerTypeAdministrationModule, BeerNoteAdministrationModule
  ],
  providers: [BeerTableComponent]
})
export class AdminPanelModule {
}
