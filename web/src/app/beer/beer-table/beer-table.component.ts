import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {Observable} from 'rxjs';
import {MatPaginator} from '@angular/material/paginator';
import {MatDialog} from '@angular/material/dialog';
import {BeerTableService} from './beer-table.service';
import {BeerDto} from '../../model/BeerDto';
import {OnClickEvent} from '../beer-dialog/click-event/on-click-event';
import {OpenDialogOnClickEvent} from '../beer-dialog/click-event/open-dialog-on-click-event';
import {BeerDialogComponent} from '../beer-dialog/beer-dialog.component';
import {ConfimationDialogComponent} from '../../sharing/confimation-dialog/confimation-dialog.component';
import {ActionType} from '../../sharing/utilities/ActionType';
import {FormGroup} from '@angular/forms';
import {BeerFilterDtoMapper} from '../beer-filter/beer-filter-dto-mapper';

@Component({
  selector: 'app-beer-table',
  templateUrl: './beer-table.component.html',
  styleUrls: ['./beer-table.component.css']
})
export class BeerTableComponent implements OnInit {
  @Input()
  displayedColumns: string[] = ['name', 'breweryName', 'barCode', 'actions'];
  dataSource;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  selectedRows: BeerDto[] = [];
  @Input()
  public allowSelection: boolean;
  @Input()
  onClickEvent: OnClickEvent<BeerDto> = new OpenDialogOnClickEvent<BeerDialogComponent, BeerDto>(this.dialog, BeerDialogComponent);
  subscription: any;

  currentFilter: FormGroup;


  constructor(public dialog: MatDialog, private beerTableService: BeerTableService) {
    this.subscription = this.beerTableService.updatedContent.subscribe(() => this.applyFilter(this.currentFilter));
  }

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.updateContent(this.beerTableService.getInitialTableContent());
  }


  onInfoClick(entry: BeerDto) {
    this.onClickEvent.onClick(entry);
  }


  applyFilter(beerFilter?: FormGroup) {
    if (beerFilter == undefined) {
      this.updateContent(this.beerTableService.getInitialTableContent());
    } else {
      this.updateContent(this.beerTableService.applyFilter(BeerFilterDtoMapper.toDto(beerFilter)));
      this.currentFilter = beerFilter;
    }
  }

  updateContent(observable: Observable<any>) {
    observable.subscribe(value => {
        let sortedValue = value.sort(function(a, b) {
          if (a.name == null) {
            return 1;
          } else if (b.name == null) {
            return -1;
          }
          return a.name.localeCompare(b.name);
        });

        this.dataSource = new MatTableDataSource(sortedValue);
        this.dataSource.paginator = this.paginator;
      },
      error => {
        console.log(error);
      });
  }

  onEditClick(element) {
    this.beerTableService.edit(element);
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
        this.beerTableService.delete(element);
      }
    });
  }

  onAddClick() {
    this.beerTableService.add();
  }
}
