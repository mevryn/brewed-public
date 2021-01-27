import {Component, EventEmitter, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ModelService} from '../../../services/model.service';
import {BeerNote} from '../../../model/BeerNote';
import {InformationDialogFactory} from '../../../sharing/information-dialog-factory';
import {Severity} from '../../../sharing/utilities/InformationDialogUtils';

@Component({
  selector: 'app-beer-note-dialog',
  templateUrl: './beer-note-dialog.component.html',
  styleUrls: ['./beer-note-dialog.component.css']
})
export class BeerNoteDialogComponent implements OnInit {
  actionTypeMessage: string;
  public eventEmitter = new EventEmitter();

  constructor(private dialogFactory: InformationDialogFactory, private modelService: ModelService,
              @Inject(MAT_DIALOG_DATA) public  data: { entryData: BeerNote },
              public dialogRef: MatDialogRef<BeerNoteDialogComponent>) {
  }

  ngOnInit(): void {
    this.actionTypeMessage = this.data.entryData.id == undefined ? 'stworzona' : 'zmodyfikowana';
  }


  provideTitle(): string {
    return this.data.entryData.id != undefined ? 'Modyfikuj nutę' : 'Dodaj nutę';
  }

  onNoClick() {
    this.dialogRef.close();
  }

  apply() {
    this.modelService.beerNoteService.postNote(this.data.entryData).subscribe(value => {
      this.eventEmitter.emit();
      this.dialogFactory.openInformationDialog(Severity.INFORMATION, 'Nuta została ' + this.actionTypeMessage);
    }, error =>
      this.dialogFactory.openInformationDialog(Severity.ERROR, error.message));
    this.dialogRef.close();
  }
}
