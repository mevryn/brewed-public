import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BeerTypeAdministrationComponent} from './beer-type-administration.component';

describe('BeerTypeAdministrationComponent', () => {
  let component: BeerTypeAdministrationComponent;
  let fixture: ComponentFixture<BeerTypeAdministrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BeerTypeAdministrationComponent]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeerTypeAdministrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
