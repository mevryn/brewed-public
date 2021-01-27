import {BreweryService} from './brewery.service';
import {TestBed} from '@angular/core/testing';

describe('BreweryServiceService', () => {
  let service: BreweryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BreweryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
