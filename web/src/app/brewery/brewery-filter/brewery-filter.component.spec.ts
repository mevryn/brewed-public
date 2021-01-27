import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BreweryFilterComponent} from './brewery-filter.component';

describe('BreweryFilterComponent', () => {
  let component: BreweryFilterComponent;
  let fixture: ComponentFixture<BreweryFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BreweryFilterComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BreweryFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
