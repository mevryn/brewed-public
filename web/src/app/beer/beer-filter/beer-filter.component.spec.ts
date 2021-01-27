import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BeerFilterComponent} from './beer-filter.component';

describe('BeerFilterComponent', () => {
  let component: BeerFilterComponent;
  let fixture: ComponentFixture<BeerFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BeerFilterComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeerFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
