import {Component, EventEmitter, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Brewery} from '../../../model/Brewery';
import {ModelService} from '../../../services/model.service';
import {InformationDialogFactory} from '../../../sharing/information-dialog-factory';
import {Severity} from '../../../sharing/utilities/InformationDialogUtils';

@Component({
  selector: 'app-brewery-dialog',
  templateUrl: './brewery-dialog.component.html',
  styleUrls: ['./brewery-dialog.component.css']
})
export class BreweryDialogComponent implements OnInit {

  actionTypeMessage: string;
  eventEmitter = new EventEmitter();

  constructor(private dialogFactory: InformationDialogFactory, private modelService: ModelService, @Inject(MAT_DIALOG_DATA) public  data: { entryData: Brewery }, public dialogRef: MatDialogRef<BreweryDialogComponent>) {
  }

  ngOnInit(): void {
    this.actionTypeMessage = this.data.entryData.id == undefined ? 'stworzony' : 'zmodyfikowany';
  }


  provideTitle(): string {
    return this.data.entryData.id != undefined ? 'Modyfikuj browar' : 'Dodaj browar';
  }

  onNoClick() {
    this.dialogRef.close();
  }

  apply() {
    this.modelService.breweryService.postBrewery(this.data.entryData).subscribe(
      value => {
        this.eventEmitter.emit();
        this.dialogFactory.openInformationDialog(Severity.INFORMATION, 'Browar został ' + this.actionTypeMessage);
      },
      error => this.dialogFactory.openInformationDialog(Severity.ERROR, error.message));
    this.dialogRef.close();
  }
}
