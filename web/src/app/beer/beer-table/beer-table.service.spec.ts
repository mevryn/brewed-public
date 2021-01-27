import {TestBed} from '@angular/core/testing';

import {BeerTableService} from './beer-table.service';

describe('BeerTableService', () => {
  let service: BeerTableService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BeerTableService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
