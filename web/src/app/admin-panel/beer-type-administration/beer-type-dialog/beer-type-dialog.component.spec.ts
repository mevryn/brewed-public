import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BeerTypeDialogComponent} from './beer-type-dialog.component';

describe('BeerTypeDialogComponent', () => {
  let component: BeerTypeDialogComponent;
  let fixture: ComponentFixture<BeerTypeDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BeerTypeDialogComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeerTypeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
