import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Severity} from '../InformationDialogUtils';

@Component({
  selector: 'app-information-dialog',
  templateUrl: './information-dialog.component.html',
  styleUrls: ['./information-dialog.component.css']
})
export class InformationDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: { severity: Severity, information: string }, public dialogRef: MatDialogRef<InformationDialogComponent>) {
  }

  ngOnInit(): void {
  }

}
