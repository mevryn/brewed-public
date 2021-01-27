import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BeerAdministrationComponent} from './beer-administration.component';

describe('BeerAdministrationComponent', () => {
  let component: BeerAdministrationComponent;
  let fixture: ComponentFixture<BeerAdministrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BeerAdministrationComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeerAdministrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
