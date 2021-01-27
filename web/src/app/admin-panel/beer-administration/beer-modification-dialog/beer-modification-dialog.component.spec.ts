import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BeerModificationDialogComponent} from './beer-modification-dialog.component';

describe('BeerModificationDialogComponent', () => {
  let component: BeerModificationDialogComponent;
  let fixture: ComponentFixture<BeerModificationDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BeerModificationDialogComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BeerModificationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
