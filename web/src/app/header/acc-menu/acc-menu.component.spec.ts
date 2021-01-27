import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AccMenuComponent} from './acc-menu.component';

describe('AccMenuComponent', () => {
  let component: AccMenuComponent;
  let fixture: ComponentFixture<AccMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AccMenuComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
