import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Store } from '@ngrx/store';
import { TranslateModule } from '@ngx-translate/core';
import { from } from 'rxjs';
import { FormValidator } from 'src/app/shared/helpers/formValidator.helper';

import { PlantationFormComponent } from './plantation-form.component';

describe('PlantationFormComponent', () => {
  let component: PlantationFormComponent;
  let fixture: ComponentFixture<PlantationFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlantationFormComponent ],
      providers: [
        { useValue: {
          dispatch: jasmine.createSpy('dispatch'),
          pipe: jasmine.createSpy('pipe').and.returnValue(from([])),
          select: jasmine.createSpy('select')
        }, provide: Store },
        { useValue: {}, provide: FormValidator },
        { useValue: {}, provide: NgbActiveModal },
        { useValue: {}, provide: Router }
      ],
      imports: [
        TranslateModule.forRoot()
      ],
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
