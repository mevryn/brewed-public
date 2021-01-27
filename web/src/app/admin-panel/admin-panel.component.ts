import {Component} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';

export const STANDARD_DIALOG_WIDTH = '60%';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent {


  constructor(public dialog: MatDialog) {
  }


}
