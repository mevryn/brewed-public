import {Injectable} from '@angular/core';
import {Severity} from './utilities/InformationDialogUtils';
import {MatDialog} from '@angular/material/dialog';
import {InformationDialogComponent} from './utilities/information-dialog/information-dialog.component';

@Injectable(
)
export class InformationDialogFactory {

  constructor(private matDialog: MatDialog) {
  }

  openInformationDialog(severity: Severity, information: string) {
    this.matDialog.open(InformationDialogComponent, {
      data: {
        severity: severity,
        information: information
      }
    });
  }
}
