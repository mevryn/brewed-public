import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BeerNoteDialogComponent} from './beer-note-dialog.component';

describe('BeerNoteDialogComponent', () => {
  let component: BeerNoteDialogComponent;
  let fixture: ComponentFixture<BeerNoteDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BeerNoteDialogComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeerNoteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
