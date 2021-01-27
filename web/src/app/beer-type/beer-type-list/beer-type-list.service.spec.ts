import {TestBed} from '@angular/core/testing';

import {BeerTypeListService} from './beer-type-list.service';

describe('BeerTypeListService', () => {
  let service: BeerTypeListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BeerTypeListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
