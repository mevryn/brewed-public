import {TestBed} from '@angular/core/testing';

import {BeerNoteListService} from './beer-note-list.service';

describe('BeerNoteListService', () => {
  let service: BeerNoteListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BeerNoteListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
