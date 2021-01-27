import {TestBed} from '@angular/core/testing';

import {BeerTypeService} from './beer-type.service';

describe('BeerTypeService', () => {
  let service: BeerTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BeerTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
