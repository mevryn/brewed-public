export class SelectionUtils {
  static handleClickSelection(selectedRows: any[], selection: any): any[] {
    if (selectedRows.includes(selection)) {
      selectedRows = selectedRows.filter(row => row !== selection);
    } else {
      selectedRows.push(selection);
    }
    return selectedRows;
  }
}
