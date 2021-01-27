import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Brewery} from '../../model/Brewery';

@Component({
  selector: 'app-brewery-info-dialog',
  templateUrl: './brewery-info-dialog.component.html',
  styleUrls: ['./brewery-info-dialog.component.css']
})
export class BreweryInfoDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<BreweryInfoDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: { entryData: Brewery }) {
  }

  ngOnInit(): void {
  }

}
