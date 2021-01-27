import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {OnClickEvent} from '../../beer/beer-dialog/click-event/on-click-event';
import {SelectOnClickEvent} from '../../beer/beer-dialog/click-event/select-on-click-event';
import {SelectionUtils} from '../../beer/beer-table/selection-utils';
import {Observable} from 'rxjs';
import {MatTableDataSource} from '@angular/material/table';
import {BeerNote} from '../../model/BeerNote';
import {ConfimationDialogComponent} from '../../sharing/confimation-dialog/confimation-dialog.component';
import {ActionType} from '../../sharing/utilities/ActionType';
import {MatDialog} from '@angular/material/dialog';
import {BeerNoteListService} from './beer-note-list.service';

@Component({
  selector: 'app-beer-note-list',
  templateUrl: './beer-note-list.component.html',
  styleUrls: ['./beer-note-list.component.css']
})
export class BeerNoteListComponent implements OnInit {

  @Input()
  displayedColumns: string[] = ['note'];
  dataSource;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  loading: boolean = false;
  selectedRows: BeerNote[] = [];
  @Input()
  public allowSelection: boolean;
  @Input()
  onClickEvent: OnClickEvent<BeerNote> = new SelectOnClickEvent();
  private subscription: any;

  constructor(public dialog: MatDialog, private listService: BeerNoteListService) {
    this.subscription = this.listService.updatedContent.subscribe(() => this.refresh());
  }

  ngOnInit(): void {
    this.refresh();
  }

  private static getSortedValue(value: BeerNote[]) {
    return value.sort(function(a, b) {
      if (a.note == null) {
        return 1;
      } else if (b.note == null) {
        return -1;
      }
      return a.note.localeCompare(b.note);
    });
  }

  onClick(row) {
    this.onClickEvent.onClick(row);
  }

  selectRow(row) {
    this.selectedRows = SelectionUtils.handleClickSelection(this.selectedRows, row);
  }

  refresh(): void {
    this.updateContent(this.listService.getAllNotes());
  }

  onEditClick(element) {
    this.listService.edit(element);
  }

  onRemoveClick(element) {
    let matDialogRef = this.dialog.open(ConfimationDialogComponent, {
      data: {
        type: ActionType.DELETE
      },
      restoreFocus: false,
      disableClose: true
    });
    matDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.listService.delete(element);
      }
    });
  }

  onAddClick() {
    this.listService.add();
  }

  private updateContent(content: Observable<BeerNote[]>) {
    content.subscribe(value => {
        let sortedValue = BeerNoteListComponent.getSortedValue(value);
        this.loading = true;
        this.dataSource = new MatTableDataSource(sortedValue);
        this.dataSource.paginator = this.paginator;
      },
      error => {
        console.log(error);
      },
      () => {
        this.loading = false;
      });
  }

  onInfoClick(element) {

  }
}
