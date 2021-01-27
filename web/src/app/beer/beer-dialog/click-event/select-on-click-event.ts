import {OnClickEvent} from './on-click-event';
import {SelectionUtils} from '../../beer-table/selection-utils';

export class SelectOnClickEvent implements OnClickEvent<any> {

  selectedRows: any = [];

  onClick(row: any) {
    this.selectedRows = SelectionUtils.handleClickSelection(this.selectedRows, row);
  }

  getSelectedRows() {
    return this.selectedRows;
  }

  clearSelection() {
    this.selectedRows = [];
  }

}
