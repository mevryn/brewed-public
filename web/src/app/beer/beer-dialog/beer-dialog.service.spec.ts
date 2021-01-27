import {TestBed} from '@angular/core/testing';

import {BeerDialogService} from './beer-dialog.service';

describe('BeerDialogService', () => {
  let service: BeerDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BeerDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
