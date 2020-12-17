import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaterPanelComponent } from './water-panel.component';

describe('WaterPanelComponent', () => {
  let component: WaterPanelComponent;
  let fixture: ComponentFixture<WaterPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WaterPanelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WaterPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
