import {Component, EventEmitter, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ModelService} from '../../../services/model.service';
import {BeerType} from '../../../model/BeerType';
import {InformationDialogFactory} from '../../../sharing/information-dialog-factory';
import {Severity} from '../../../sharing/utilities/InformationDialogUtils';

@Component({
  selector: 'app-beer-type-dialog',
  templateUrl: './beer-type-dialog.component.html',
  styleUrls: ['./beer-type-dialog.component.css']
})
export class BeerTypeDialogComponent implements OnInit {
  actionTypeMessage: string;
  public eventEmitter = new EventEmitter();

  constructor(private dialogFactory: InformationDialogFactory, private modelService: ModelService, @Inject(MAT_DIALOG_DATA) public  data: { entryData: BeerType }, public dialogRef: MatDialogRef<BeerTypeDialogComponent>) {
  }

  ngOnInit(): void {
    this.actionTypeMessage = this.data.entryData.id == undefined ? 'stworzony' : 'zmodyfikowany';
  }


  provideTitle(): string {
    return this.data.entryData.id != undefined ? 'Modyfikuj typ' : 'Dodaj typ';
  }

  onNoClick() {
    this.dialogRef.close();
  }

  apply() {
    this.modelService.beerTypeService.postType(this.data.entryData).subscribe(value => {
      this.eventEmitter.emit();
      this.dialogFactory.openInformationDialog(Severity.INFORMATION, 'Typ został ' + this.actionTypeMessage);
    }, error => this.dialogFactory.openInformationDialog(Severity.ERROR, error.message));
    this.dialogRef.close();
  }
}
