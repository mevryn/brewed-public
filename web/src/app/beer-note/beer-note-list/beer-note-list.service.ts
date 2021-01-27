import {EventEmitter, Injectable} from '@angular/core';
import {BeerNoteService} from '../../services/beer-note.service';
import {STANDARD_DIALOG_WIDTH} from '../../admin-panel/admin-panel.component';
import {BeerNote} from '../../model/BeerNote';
import {MatDialog} from '@angular/material/dialog';
import {BeerNoteDialogComponent} from '../../admin-panel/beer-note-administration/beer-note-dialog/beer-note-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class BeerNoteListService {

  public updatedContent = new EventEmitter;

  constructor(private dialog: MatDialog, private beerNoteService: BeerNoteService) {
  }

  edit(element) {
    let matDialogRef = this.dialog.open(BeerNoteDialogComponent, {
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
    this.beerNoteService.delete(element).subscribe(() => this.updatedContent.emit());
  }

  add() {
    let matDialogRef = this.dialog.open(BeerNoteDialogComponent, {
      width: STANDARD_DIALOG_WIDTH,
      restoreFocus: false,
      data: {
        entryData: new BeerNote()
      }
    });
    matDialogRef.componentInstance
      .eventEmitter
      .subscribe(() => this.updatedContent.emit());
  }

  getAllNotes() {
    return this.beerNoteService.getAllNotes();
  }
}
