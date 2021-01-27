import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AccMenuComponent} from './acc-menu.component';
import {_MatMenuDirectivesModule, MatMenuModule} from "@angular/material/menu";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {RouterModule} from "@angular/router";
import {LoginDialogComponent} from './login-dialog/login-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [AccMenuComponent, LoginDialogComponent],
  exports: [
    AccMenuComponent
  ],
  imports: [
    CommonModule,
    _MatMenuDirectivesModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    RouterModule,
    MatDialogModule,
    FormsModule
  ]
})
export class AccMenuModule {
}
