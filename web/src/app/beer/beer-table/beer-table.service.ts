import {EventEmitter, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {BeerDto} from '../../model/BeerDto';
import {BeerService} from '../../services/beer.service';
import {MatDialog} from '@angular/material/dialog';
import {BeerModificationDialogComponent} from '../../admin-panel/beer-administration/beer-modification-dialog/beer-modification-dialog.component';
import {AdditionDialogComponent} from '../../admin-panel/beer-administration/addition-dialog/addition-dialog.component';
import {STANDARD_DIALOG_WIDTH} from '../../admin-panel/admin-panel.component';
import {BeerFilterDto} from '../beer-filter/beer-filter-dto';
import {Beer} from '../../model/Beer';

@Injectable({
  providedIn: 'root'
})
export class BeerTableService {

  updatedContent: EventEmitter<number> = new EventEmitter();


  constructor(private http: HttpClient, private dialog: MatDialog, private beerService: BeerService) {
  }

  getInitialTableContent(): Observable<Beer[]> {
    return this.beerService.getAllBeers();
  }


  edit(element) {
    let matDialogRef = this.dialog.open(BeerModificationDialogComponent, {
      width: STANDARD_DIALOG_WIDTH,
      restoreFocus: false,
      data: {
        entryData: element
      }
    });
    matDialogRef.componentInstance
      .eventEmmiter
      .subscribe(() => this.updatedContent.emit());
  }

  delete(element) {
    this.beerService.deleteBeer(element).subscribe(() => this.updatedContent.emit());
  }

  add() {
    let matDialogRef = this.dialog.open(AdditionDialogComponent, {
      width: STANDARD_DIALOG_WIDTH,
      restoreFocus: false,
      data: {
        entryData: new BeerDto()
      }
    });
    matDialogRef.componentInstance
      .eventEmmiter
      .subscribe(() => this.updatedContent.emit());
  }

  applyFilter(beerFilter: BeerFilterDto): Observable<Beer[]> {
    return this.beerService.getFilteredBeers(beerFilter);
  }
}
