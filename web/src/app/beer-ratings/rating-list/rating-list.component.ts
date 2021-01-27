import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatDialog} from '@angular/material/dialog';
import {BeerRatingsService} from '../beer-ratings.service';
import {FilterDto} from '../rating-filter/filter-dto';
import {MatTableDataSource} from '@angular/material/table';
import {ConfimationDialogComponent} from '../../sharing/confimation-dialog/confimation-dialog.component';
import {ActionType} from '../../sharing/utilities/ActionType';
import {OpenDialogOnClickEvent} from '../../beer/beer-dialog/click-event/open-dialog-on-click-event';
import {RatingsInfoDialogComponent} from '../ratings-info-dialog/ratings-info-dialog.component';
import {BeerRatingDto} from '../beer-rating-dto';
import {STANDARD_DIALOG_WIDTH} from '../../admin-panel/admin-panel.component';
import {RatingInputComponent} from '../rating-input/rating-input.component';

@Component({
  selector: 'app-rating-list',
  templateUrl: './rating-list.component.html',
  styleUrls: ['./rating-list.component.css']
})
export class RatingListComponent implements OnInit {
  @Input()
  displayedColumns: string[] = ['userIdentifier', 'beerName', 'actions'];
  dataSource;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @Input()
  public allowSelection: boolean;
  @Input()
  subscription: any;
  dialogOpenEvent = new OpenDialogOnClickEvent(this.dialog, RatingsInfoDialogComponent);
  currentFilter = new FilterDto();

  constructor(private dialog: MatDialog, private beerRatingsService: BeerRatingsService) {
    this.subscription = beerRatingsService.updatedContent
      .subscribe(() => this.refresh(this.currentFilter));
  }

  ngOnInit(): void {
    this.refresh(new FilterDto());
  }

  onAddClick() {
    this.postRating();
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
        this.beerRatingsService.deleteRating(element);
      }
    });
  }

  onEditClick(element) {
    this.postRating(element);
  }

  postRating(rating?) {
    if (rating == undefined) {
      this.openInputDialog(new BeerRatingDto());
    } else {
      this.beerRatingsService.getBeerRating(rating).subscribe(value => this.openInputDialog(value));
    }
  }

  refresh(filter: FilterDto) {
    this.beerRatingsService.applyFilter(filter).subscribe(value => {
      this.currentFilter = filter;
      this.dataSource = new MatTableDataSource(value);
      this.dataSource.paginator = this.paginator;
    });
  }

  onInfoClick(element) {
    this.dialogOpenEvent.onClick(element);
  }

  private openInputDialog(beerRatingDto: BeerRatingDto) {
    let matDialogRef = this.dialog.open(RatingInputComponent, {
      width: STANDARD_DIALOG_WIDTH,
      restoreFocus: false,
      data: {
        entryData: beerRatingDto
      }
    });
    matDialogRef.componentInstance
      .eventEmitter
      .subscribe(() => this.refresh(this.currentFilter));
  }
}
