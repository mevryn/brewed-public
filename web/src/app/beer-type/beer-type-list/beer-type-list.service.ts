import {EventEmitter, Injectable} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {STANDARD_DIALOG_WIDTH} from '../../admin-panel/admin-panel.component';
import {BeerTypeService} from '../../services/beer-type.service';
import {BeerTypeDialogComponent} from '../../admin-panel/beer-type-administration/beer-type-dialog/beer-type-dialog.component';
import {BeerType} from '../../model/BeerType';

@Injectable({
  providedIn: 'root'
})
export class BeerTypeListService {
  public updatedContent = new EventEmitter;

  constructor(private dialog: MatDialog, private beerTypeService: BeerTypeService) {
  }

  edit(element) {
    let matDialogRef = this.dialog.open(BeerTypeDialogComponent, {
      width: STANDARD_DIALOG_WIDTH,
      restoreFocus: false,
      data: {
        entryData: element
      }
    });
    matDialogRef.componentInstance
      .eventEmitter
      .subscribe(() => this.updatedContent.emit());
  }

  delete(element) {
    this.beerTypeService.delete(element).subscribe(() => this.updatedContent.emit());
  }

  add() {
    let matDialogRef = this.dialog.open(BeerTypeDialogComponent, {
      width: STANDARD_DIALOG_WIDTH,
      restoreFocus: false,
      data: {
        entryData: new BeerType()
      }
    });
    matDialogRef.componentInstance
      .eventEmitter
      .subscribe(() => this.updatedContent.emit());
  }

  getAllTypes() {
    return this.beerTypeService.getAllTypes();
  }
}
