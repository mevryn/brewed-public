import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {LoginDialogComponent} from './login-dialog/login-dialog.component';
import {AppService} from '../../app.service';

@Component({
  selector: 'app-acc-menu',
  templateUrl: './acc-menu.component.html',
  styleUrls: ['./acc-menu.component.css']
})
export class AccMenuComponent implements OnInit {

  constructor(public appService: AppService, public dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  onLoginClick() {
    this.dialog.open(LoginDialogComponent);
  }

  onLogoutClick() {
    this.appService.logout();
  }
}
