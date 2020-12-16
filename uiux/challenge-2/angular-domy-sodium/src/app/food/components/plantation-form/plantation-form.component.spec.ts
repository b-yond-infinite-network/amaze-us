import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlantationFormComponent } from './plantation-form.component';

describe('PlantationFormComponent', () => {
  let component: PlantationFormComponent;
  let fixture: ComponentFixture<PlantationFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlantationFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlantationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
