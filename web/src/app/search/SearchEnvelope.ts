import {FormGroup} from '@angular/forms';

export class SearchEnvelope {
  id: string;
  barCode: string;
  name: string;
  breweryName: string;

  constructor(id?: string, barCode?: string, name?: string, breweryName?: string) {
    this.id = id;
    this.barCode = barCode;
    this.name = name;
    this.breweryName = breweryName;
  }

  isblank(): boolean {
    return !this.id && !this.barCode && !this.name && !this.breweryName;
  }
}

export class SearchEnvelopeFactory {
  public static create(formGroup: FormGroup): SearchEnvelope {
    let searchEnvelope = new SearchEnvelope();
    searchEnvelope.id = formGroup.get('id') != null ? formGroup.get('id').value : null;
    searchEnvelope.barCode = formGroup.get('barCode') != null ? formGroup.get('barCode').value : null;
    searchEnvelope.name = formGroup.get('name') != null ? formGroup.get('name').value : null;
    searchEnvelope.breweryName = formGroup.get('breweryName') != null ? formGroup.get('breweryName').value : null;
    return searchEnvelope;
  }
}
