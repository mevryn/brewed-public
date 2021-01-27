import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BeerRatingsComponent} from './beer-ratings.component';

describe('BeerRatingsComponent', () => {
  let component: BeerRatingsComponent;
  let fixture: ComponentFixture<BeerRatingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BeerRatingsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeerRatingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
