import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BreweryInfoDialogComponent} from './brewery-info-dialog.component';

describe('BreweryInfoDialogComponent', () => {
  let component: BreweryInfoDialogComponent;
  let fixture: ComponentFixture<BreweryInfoDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BreweryInfoDialogComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BreweryInfoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
