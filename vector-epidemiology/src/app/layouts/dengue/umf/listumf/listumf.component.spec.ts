import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListumfComponent } from './listumf.component';

describe('ListumfComponent', () => {
  let component: ListumfComponent;
  let fixture: ComponentFixture<ListumfComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListumfComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListumfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
