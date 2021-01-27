import {Component, Inject, NgZone, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {BeerDto} from '../../model/BeerDto';
import {BeerDialogService} from './beer-dialog.service';
import {Brewery} from '../../model/Brewery';
import {Beer} from '../../model/Beer';

@Component({
  selector: 'app-beer-dialog',
  templateUrl: './beer-dialog.component.html',
  styleUrls: ['./beer-dialog.component.css']
})
export class BeerDialogComponent implements OnInit {

  brewery = new Brewery();
  type: string;
  note: string;

  constructor(
    public dialogRef: MatDialogRef<BeerDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { entryData: Beer },
    private  ngZone: NgZone,
    private  service: BeerDialogService) {
  }

  onNoClick(): void {
    this.ngZone.run(() => {
      this.dialogRef.close();
    });
  }

  ngOnInit(): void {
    this.service.getBrewery(this.data.entryData.brewery.id).subscribe(
      value => {
        this.brewery = value;
      }
    );
    this.service.getNote(this.data.entryData.beerNote.id).subscribe(
      value => {
        this.note = value.note;
      }
    );
    this.service.getType(this.data.entryData.beerType.id).subscribe(
      value => {
        this.type = value.type;
      }
    );
  }

}

export interface DialogData {
  beer: BeerDto;
}
