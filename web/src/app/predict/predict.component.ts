import {Component, OnInit, Renderer2, ViewChild} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {CaptureComponent} from '../capture/capture.component';
import {PredictService} from './predict.service';
import {InformationDialogFactory} from '../sharing/information-dialog-factory';
import {Severity} from '../sharing/utilities/InformationDialogUtils';

@Component({
  selector: 'app-predict',
  templateUrl: './predict.component.html',
  styleUrls: ['./predict.component.css']
})
export class PredictComponent implements OnInit {
  @ViewChild('table') tableComponent;
  url;
  img;

  constructor(private dialogFactory: InformationDialogFactory, private renderer: Renderer2, public dialog: MatDialog, private predictService: PredictService) {
  }

  ngOnInit(): void {
    this.url = 'assets/beer-placeholder.jpg';
  }

  onPredictClick() {
    this.tableComponent.updateContent(this.predictService.getPredictedBeer());
  }

  onFileChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.predictService.file = (target.files as FileList)[0];
    var reader = new FileReader();
    reader.onload = (event: any) => {
      this.url = event.target.result;
    };
    reader.onerror = (event: any) => {
      console.log('File could not be read: ' + event.target.error.code);
    };
    reader.readAsDataURL((target.files as FileList)[0]);
  };

  openCapture() {
    let dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.maxWidth = '30%';
    dialogConfig.maxHeight = '40%';
    dialogConfig.panelClass = 'capture';
    dialogConfig.disableClose = true;
    const dialogRef = this.dialog.open(CaptureComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
        this.predictService.file = result.toDataURL();
        this.url = this.predictService.file;
      }, error => this.dialogFactory.openInformationDialog(Severity.ERROR, 'Rozpoznanie obrazu nie powiodło się z' +
      ' powodu błędu serwera. Skontaktuj się z deweloperem.')
    )
    ;
  }

}
