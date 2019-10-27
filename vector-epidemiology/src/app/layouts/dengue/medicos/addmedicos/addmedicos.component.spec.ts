import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddmedicosComponent } from './addmedicos.component';

describe('AddmedicosComponent', () => {
  let component: AddmedicosComponent;
  let fixture: ComponentFixture<AddmedicosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddmedicosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddmedicosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
