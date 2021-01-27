import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BreweryAdministrationComponent} from './brewery-administration.component';

describe('BreweryAdministrationComponent', () => {
  let component: BreweryAdministrationComponent;
  let fixture: ComponentFixture<BreweryAdministrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BreweryAdministrationComponent]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BreweryAdministrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
