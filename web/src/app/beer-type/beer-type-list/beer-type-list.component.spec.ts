import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BeerTypeListComponent} from './beer-type-list.component';

describe('BeerTypeListComponent', () => {
  let component: BeerTypeListComponent;
  let fixture: ComponentFixture<BeerTypeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BeerTypeListComponent]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeerTypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
