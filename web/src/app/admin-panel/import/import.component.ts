import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ImportService} from './import.service';

@Component({
  selector: 'app-import',
  templateUrl: './import.component.html',
  styleUrls: ['./import.component.css']
})
export class ImportComponent implements OnInit {

  file: File;
  @Output() importPerformed = new EventEmitter();

  constructor(private service: ImportService) {
  }

  ngOnInit(): void {
  }

  onFileChange(event: Event) {
    this.file = this.service.onFileChange(event);
  }

  onImportClick() {
    this.service.importFromFile(this.file).subscribe(() => this.importPerformed.emit());
  }
}
