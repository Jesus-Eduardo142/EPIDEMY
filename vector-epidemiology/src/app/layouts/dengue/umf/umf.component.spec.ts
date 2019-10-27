import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UmfComponent } from './umf.component';

describe('UmfComponent', () => {
  let component: UmfComponent;
  let fixture: ComponentFixture<UmfComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UmfComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UmfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
