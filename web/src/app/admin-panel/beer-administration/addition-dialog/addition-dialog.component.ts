import {Component, EventEmitter, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ModelService} from '../../../services/model.service';
import {Brewery} from '../../../model/Brewery';
import {BeerType} from '../../../model/BeerType';
import {BeerNote} from '../../../model/BeerNote';
import {InformationDialogFactory} from '../../../sharing/information-dialog-factory';
import {Severity} from '../../../sharing/utilities/InformationDialogUtils';
import {Beer} from '../../../model/Beer';
import {BeerMapper} from '../../../sharing/beer-mapper';

@Component({
  selector: 'app-addition-dialog',
  templateUrl: './addition-dialog.component.html',
  styleUrls: ['./addition-dialog.component.css']
})
export class AdditionDialogComponent implements OnInit {


  public breweries: Brewery[];
  public beerTypes: BeerType[];
  public beerNotes: BeerNote[];

  public brewery: any;
  public beerNote: any;
  public beerType: any;
  public eventEmmiter = new EventEmitter();

  constructor(private dialogFactory: InformationDialogFactory, private modelService: ModelService, public dialogRef: MatDialogRef<AdditionDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: { entryData: Beer }) {
  }

  onNoClick() {
    this.dialogRef.close();
  }

  add() {
    if (this.data.entryData.name == undefined) {
      this.dialogFactory.openInformationDialog(Severity.ERROR, 'Nazwa piwa nie została zdefiniowana');
    }
    this.modelService.beerService.addBeer(BeerMapper.toDto(this.data.entryData)).subscribe(
      value => {
        this.eventEmmiter.emit();
        this.dialogFactory.openInformationDialog(Severity.INFORMATION, 'Piwo o nazwie: ' + value.name + ' zostało' +
          ' stworzone');
      }, error => {
        console.log(error);
        this.dialogFactory.openInformationDialog(Severity.ERROR, error.error.message);
      }, () => {

      }
    );
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.modelService.breweryService.getAllBreweries()
      .subscribe(value => this.breweries = value);
    this.modelService.beerTypeService.getAllTypes()
      .subscribe(value => this.beerTypes = value);
    this.modelService.beerNoteService.getAllNotes()
      .subscribe(value => this.beerNotes = value);
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

}
