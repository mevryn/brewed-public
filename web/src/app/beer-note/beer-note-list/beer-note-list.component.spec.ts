import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BeerNoteListComponent} from './beer-note-list.component';

describe('BeerNoteListComponent', () => {
  let component: BeerNoteListComponent;
  let fixture: ComponentFixture<BeerNoteListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BeerNoteListComponent]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeerNoteListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
