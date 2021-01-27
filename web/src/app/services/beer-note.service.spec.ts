import {TestBed} from '@angular/core/testing';

import {BeerNoteService} from './beer-note.service';

describe('BeerNoteService', () => {
  let service: BeerNoteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BeerNoteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
