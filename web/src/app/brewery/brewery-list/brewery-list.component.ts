import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {Observable} from 'rxjs';
import {MatTableDataSource} from '@angular/material/table';
import {Brewery} from '../../model/Brewery';
import {OnClickEvent} from '../../beer/beer-dialog/click-event/on-click-event';
import {BreweryListService} from './brewery-list.service';
import {ConfimationDialogComponent} from '../../sharing/confimation-dialog/confimation-dialog.component';
import {ActionType} from '../../sharing/utilities/ActionType';
import {MatDialog} from '@angular/material/dialog';
import {OpenDialogOnClickEvent} from '../../beer/beer-dialog/click-event/open-dialog-on-click-event';
import {BreweryInfoDialogComponent} from '../brewery-info-dialog/brewery-info-dialog.component';
import {Severity} from '../../sharing/utilities/InformationDialogUtils';
import {InformationDialogFactory} from '../../sharing/information-dialog-factory';
import {FormGroup} from '@angular/forms';
import {BreweryFilterDtoMapper} from '../brewery-filter/brewery-filter-dto-mapper';

@Component({
  selector: 'app-brewery-list',
  templateUrl: './brewery-list.component.html',
  styleUrls: ['./brewery-list.component.css']
})
export class BreweryListComponent implements OnInit {
  @Input()
  displayedColumns: string[] = ['name'];
  dataSource;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  loading: boolean = false;
  selectedRows: Brewery[] = [];
  @Input()
  public allowSelection: boolean;
  onClickEvent: OnClickEvent<Brewery> = new OpenDialogOnClickEvent(this.dialog, BreweryInfoDialogComponent);
  private subscription: any;
  private breweryFilter: FormGroup;

  constructor(private dialogFactory: InformationDialogFactory, private dialog: MatDialog, private breweryListService: BreweryListService) {
    this.subscription = this.breweryListService.updatedContent.subscribe(() => this.applyFilter(this.breweryFilter));
  }

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.updateContent(this.breweryListService.getAllBreweries());
  }

  onAddClick() {
    this.breweryListService.add();
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
        this.breweryListService.delete(element);
      }
    });
  }

  onEditClick(element) {
    this.breweryListService.edit(element);
  }

  private updateContent(content: Observable<Brewery[]>) {
    content.subscribe(value => {
        let sortedValue = value.sort(function(a, b) {
          if (a.name == null) {
            return 1;
          } else if (b.name == null) {
            return -1;
          }
          return a.name.localeCompare(b.name);
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
    this.onClickEvent.onClick(element);
  }

  onReportClick(element: Brewery) {
    this.breweryListService.generateReport(element).subscribe(value => {
      const c = JSON.stringify(value);
      const file = new Blob([c], {type: 'text/json'});
      this.download(file, element.name + '-report.json');
      this.dialogFactory.openInformationDialog(Severity.INFORMATION,
        'Rozpoczęto pobieranie raportu.');
    });
  }

  download(blob, filename) {
    if (window.navigator.msSaveOrOpenBlob) // IE10+
      window.navigator.msSaveOrOpenBlob(blob, filename);
    else { // Others
      var a = document.createElement('a'),
        url = URL.createObjectURL(blob);
      a.href = url;
      a.download = filename;
      document.body.appendChild(a);
      a.click();
      setTimeout(function() {
        document.body.removeChild(a);
        window.URL.revokeObjectURL(url);
      }, 0);
    }
  }

  applyFilter(breweryFilter: FormGroup) {
    if (breweryFilter == undefined) {
      this.updateContent(this.breweryListService.getAllBreweries());
    } else {
      this.updateContent(this.breweryListService.applyFilter(BreweryFilterDtoMapper.toDto(breweryFilter)));
      this.breweryFilter = breweryFilter;
    }
  }
}
