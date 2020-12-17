import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { TranslateModule } from '@ngx-translate/core';
import { from } from 'rxjs';
import { FormValidator } from 'src/app/shared/helpers/formValidator.helper';
import { AuthenticationService } from '../../services/authentication.service';

import { RegisterComponent } from './register.component';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterComponent ],
      providers: [
        { provide: FormValidator, useValue: {} },
        { provide: AuthenticationService, useValue: {} },
        { provide: Store, useValue: {
          dispatch: jasmine.createSpy('dispatch'),
          pipe: jasmine.createSpy('pipe').and.returnValue(from([])),
          select: jasmine.createSpy('select')
        } },
        { provide: Router, useValue: {} }
      ],
      imports: [
        TranslateModule.forRoot()
      ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
