import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { TranslateModule } from '@ngx-translate/core';
import { from } from 'rxjs';
import { FormValidator } from 'src/app/shared/helpers/formValidator.helper';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      providers: [
        { provide: FormValidator, useValue: {} },
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
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
