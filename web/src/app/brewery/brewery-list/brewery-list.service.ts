import {EventEmitter, Injectable} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {STANDARD_DIALOG_WIDTH} from '../../admin-panel/admin-panel.component';
import {BreweryDialogComponent} from '../../admin-panel/brewery-administration/brewery-dialog/brewery-dialog.component';
import {BreweryService} from '../../services/brewery.service';
import {Brewery} from '../../model/Brewery';
import {BreweryFilterDto} from '../brewery-filter/brewery-filter-dto';

@Injectable({
  providedIn: 'root'
})
export class BreweryListService {

  public updatedContent = new EventEmitter;

  constructor(private dialog: MatDialog, private breweryService: BreweryService) {
  }

  edit(element) {
    let matDialogRef = this.dialog.open(BreweryDialogComponent, {
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
    this.breweryService.delete(element).subscribe(() => this.updatedContent.emit());
  }

  add() {
    let matDialogRef = this.dialog.open(BreweryDialogComponent, {
      width: STANDARD_DIALOG_WIDTH,
      restoreFocus: false,
      data: {
        entryData: new Brewery()
      }
    });
    matDialogRef.componentInstance
      .eventEmitter
      .subscribe(() => this.updatedContent.emit());
  }

  getAllBreweries() {
    return this.breweryService.getAllBreweries();
  }

  generateReport(element: Brewery) {
    return this.breweryService.generateReport(element);
  }

  applyFilter(breweryFilterDto: BreweryFilterDto) {
    return this.breweryService.getFilteredBreweries(breweryFilterDto);
  }
}
