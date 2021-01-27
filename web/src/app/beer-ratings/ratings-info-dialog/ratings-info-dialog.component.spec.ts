import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RatingsInfoDialogComponent} from './ratings-info-dialog.component';

describe('RatingsInfoDialogComponent', () => {
  let component: RatingsInfoDialogComponent;
  let fixture: ComponentFixture<RatingsInfoDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RatingsInfoDialogComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RatingsInfoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
