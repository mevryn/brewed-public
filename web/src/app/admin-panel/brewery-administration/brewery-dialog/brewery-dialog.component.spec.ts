import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BreweryDialogComponent} from './brewery-dialog.component';

describe('BreweryDialogComponent', () => {
  let component: BreweryDialogComponent;
  let fixture: ComponentFixture<BreweryDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BreweryDialogComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BreweryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
