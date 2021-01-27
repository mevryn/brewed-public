import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BeerTableComponent} from './beer-table.component';

describe('BeerTableComponent', () => {
  let component: BeerTableComponent;
  let fixture: ComponentFixture<BeerTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BeerTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeerTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
