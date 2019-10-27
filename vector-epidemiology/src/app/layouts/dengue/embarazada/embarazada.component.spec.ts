import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmbarazadaComponent } from './embarazada.component';

describe('EmbarazadaComponent', () => {
  let component: EmbarazadaComponent;
  let fixture: ComponentFixture<EmbarazadaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmbarazadaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmbarazadaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
