import {TestBed} from '@angular/core/testing';

import {BeerRatingsService} from './beer-ratings.service';

describe('BeerRatingsService', () => {
  let service: BeerRatingsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BeerRatingsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
