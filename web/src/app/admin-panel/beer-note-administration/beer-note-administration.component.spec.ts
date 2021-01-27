import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BeerNoteAdministrationComponent} from './beer-note-administration.component';

describe('BeerNoteAdministrationComponent', () => {
  let component: BeerNoteAdministrationComponent;
  let fixture: ComponentFixture<BeerNoteAdministrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BeerNoteAdministrationComponent]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeerNoteAdministrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
