import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HeaderComponent} from './header.component';
import {MatButtonModule} from "@angular/material/button";
import {MatMenuModule} from "@angular/material/menu";
import {MatIconModule} from "@angular/material/icon";
import {RouterModule} from "@angular/router";
import {AppMenuComponent} from './app-menu/app-menu.component';
import {AccMenuModule} from "./acc-menu/acc-menu.module";


@NgModule({
  declarations: [HeaderComponent, AppMenuComponent],
  exports: [
    HeaderComponent
  ],
  imports: [
    CommonModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    RouterModule,
    AccMenuModule
  ]
})
export class HeaderModule { }
