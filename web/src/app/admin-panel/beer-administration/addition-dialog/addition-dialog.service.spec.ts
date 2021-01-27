import {TestBed} from '@angular/core/testing';

import {AdditionDialogService} from './addition-dialog.service';

describe('AdditionDialogService', () => {
  let service: AdditionDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdditionDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
