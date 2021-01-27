import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {OnClickEvent} from '../../beer/beer-dialog/click-event/on-click-event';
import {SelectionUtils} from '../../beer/beer-table/selection-utils';
import {Observable} from 'rxjs';
import {MatTableDataSource} from '@angular/material/table';
import {BeerType} from '../../model/BeerType';
import {BeerTypeListService} from './beer-type-list.service';
import {ConfimationDialogComponent} from '../../sharing/confimation-dialog/confimation-dialog.component';
import {ActionType} from '../../sharing/utilities/ActionType';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-beer-type-list',
  templateUrl: './beer-type-list.component.html',
  styleUrls: ['./beer-type-list.component.css']
})
export class BeerTypeListComponent implements OnInit {
  @Input()
  displayedColumns: string[] = ['type'];
  dataSource;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  loading: boolean = false;
  selectedRows: BeerType[] = [];
  @Input()
  public allowSelection: boolean;
  @Input()
  onClickEvent: OnClickEvent<BeerType>;
  subscription: any;


  constructor(private dialog: MatDialog, private beerTypeListService: BeerTypeListService) {
    this.subscription = this.beerTypeListService.updatedContent.subscribe(() => this.refresh());
  }

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.updateContent(this.beerTypeListService.getAllTypes());
  }

  onClick(row) {
    this.onClickEvent.onClick(row);
  }

  selectRow(row) {
    this.selectedRows = SelectionUtils.handleClickSelection(this.selectedRows, row);
  }

  onAddClick() {
    this.beerTypeListService.add();
  }

  onRemoveClick(element) {
    let matDialogRef = this.dialog.open(ConfimationDialogComponent, {
      data: {
        type: ActionType.DELETE
      },
      restoreFocus: false,
      disableClose: true
    });
    matDialogRef.afterClosed().subscribe(value => {
      if (value) {
        this.beerTypeListService.delete(element);
      }
    });
  }

  onEditClick(element) {
    this.beerTypeListService.edit(element);
  }

  private updateContent(content: Observable<BeerType[]>) {
    content.subscribe(value => {
        let sortedValue = value.sort(function(a, b) {
          if (a.type == null) {
            return 1;
          } else if (b.type == null) {
            return -1;
          }
          return a.type.localeCompare(b.type);
        });
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
