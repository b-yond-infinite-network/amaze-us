import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopmoodsComponent } from './topmoods.component';

describe('TopmoodsComponent', () => {
  let component: TopmoodsComponent;
  let fixture: ComponentFixture<TopmoodsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopmoodsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopmoodsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
