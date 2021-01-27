import {Component, EventEmitter, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Brewery} from '../../../model/Brewery';
import {BeerType} from '../../../model/BeerType';
import {BeerNote} from '../../../model/BeerNote';
import {ModelService} from '../../../services/model.service';
import {InformationDialogFactory} from '../../../sharing/information-dialog-factory';
import {Severity} from '../../../sharing/utilities/InformationDialogUtils';
import {Beer} from '../../../model/Beer';
import {BeerMapper} from '../../../sharing/beer-mapper';

@Component({
  selector: 'app-beer-modification-dialog',
  templateUrl: './beer-modification-dialog.component.html',
  styleUrls: ['./beer-modification-dialog.component.css']
})
export class BeerModificationDialogComponent implements OnInit {

  public breweries: Brewery[];
  public beerTypes: BeerType[];
  public beerNotes: BeerNote[];

  public eventEmmiter = new EventEmitter();


  constructor(private dialogFactory: InformationDialogFactory, private modelService: ModelService, @Inject(MAT_DIALOG_DATA) public data: { entryData: Beer },
              public dialogRef: MatDialogRef<BeerModificationDialogComponent>) {
  }

  onNoClick() {
    this.dialogRef.close();
  }

  apply() {
    this.modelService.beerService.modifyBeer(BeerMapper.toDto(this.data.entryData)).subscribe(
      value => {
        this.eventEmmiter.emit();
        this.dialogFactory.openInformationDialog(Severity.INFORMATION, 'Piwo zostało zmodyfikowane pomyślnie');
      }, error =>
        this.dialogFactory.openInformationDialog(Severity.ERROR, error.message)
    );
    this.dialogRef.close(this.data.entryData);
  }

  ngOnInit(): void {
    this.modelService.breweryService.getAllBreweries()
      .subscribe(value => this.breweries = value);
    this.modelService.beerTypeService.getAllTypes()
      .subscribe(value => this.beerTypes = value);
    this.modelService.beerNoteService.getAllNotes()
      .subscribe(value => {
        this.beerNotes = value;
      });
  }

  validateWhite(event: number) {

    if (event > 100) {
      this.data.entryData.alcoholPercentage = 100;
    } else if (event < 0) {
      this.data.entryData.alcoholPercentage = 0;
    } else {
      this.data.entryData.alcoholPercentage = event;
    }
  }

  compareFn(c1: BeerNote, c2: BeerNote): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }
}
