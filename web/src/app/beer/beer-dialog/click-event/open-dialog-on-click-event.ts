import {OnClickEvent} from './on-click-event';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';

export class OpenDialogOnClickEvent<T, Type> implements OnClickEvent<Type> {

  constructor(private dialog: MatDialog, private TCtor: new (...args: any[]) => T) {
  }

  onClick(entry: Type) {
    this.openDialog(entry, this.TCtor);
  }

  private openDialog<T>(entry: Type, TCtor: new (...args: any[]) => T) {
    let dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '40%';
    dialogConfig.restoreFocus = false;
    dialogConfig.data = {
      entryData: entry
    };
    const dialogRef = this.dialog.open<T>(TCtor, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
      },
      error => {
        console.log(error);
      });
  }

}
