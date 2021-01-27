import {TestBed} from '@angular/core/testing';

import {BreweryListService} from './brewery-list.service';

describe('BreweryListService', () => {
  let service: BreweryListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BreweryListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
