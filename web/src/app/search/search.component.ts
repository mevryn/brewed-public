import {Component, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MatPaginatorIntl} from '@angular/material/paginator';
import {MatPaginatorIntlNaming} from './mat-paginator-intl-naming.service';
import {FormControl, FormGroup} from '@angular/forms';
import {SearchService} from './search.service';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
  providers: [
    {provide: MatPaginatorIntl, useClass: MatPaginatorIntlNaming}
  ]
})


export class SearchComponent {
  beerForm: FormGroup;
  @ViewChild('table') tableComponent;

  constructor(private dialog: MatDialog, private http: HttpClient, private searchService: SearchService) {
    this.beerForm = this.createFormGroup();
  }

  onSearchClick() {
    this.tableComponent.updateContent(this.searchService.search(this.beerForm));
  }

  createFormGroup() {
    return new FormGroup({
      name: new FormControl(),
      breweryName: new FormControl()
    });
  }

  keyPress($event: KeyboardEvent) {

  }
}

