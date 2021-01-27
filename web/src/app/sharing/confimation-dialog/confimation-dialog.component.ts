import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ActionType} from '../utilities/ActionType';

@Component({
  selector: 'app-confimation-dialog',
  templateUrl: './confimation-dialog.component.html',
  styleUrls: ['./confimation-dialog.component.css']
})
export class ConfimationDialogComponent implements OnInit {

  confirmation: boolean = false;

  constructor(@Inject(MAT_DIALOG_DATA) public data: { type: ActionType }, public dialogRef: MatDialogRef<ConfimationDialogComponent>) {
  }

  ngOnInit(): void {
  }

  onNoClick() {
    this.dialogRef.close(false);
  }

  onApply() {
    this.dialogRef.close(true);
  }
}
