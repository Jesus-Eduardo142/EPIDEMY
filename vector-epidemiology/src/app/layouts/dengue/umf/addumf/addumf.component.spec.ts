import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddumfComponent } from './addumf.component';

describe('AddumfComponent', () => {
  let component: AddumfComponent;
  let fixture: ComponentFixture<AddumfComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddumfComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddumfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
