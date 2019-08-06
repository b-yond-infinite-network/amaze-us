import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserComponent } from './add-user.component';
import { Router } from '@angular/router';
import { UsersService } from '../service/users.service';
import { ReactiveFormsModule } from '@angular/forms';

describe('AddUserComponent', () => {
  let component: AddUserComponent;
  let fixture: ComponentFixture<AddUserComponent>;
  let usersService : jasmine.SpyObj<UsersService>;
  let router: jasmine.SpyObj<Router>;

  beforeEach(async(() => {
    const usersServiceSpy = jasmine.createSpyObj('UsersService', ['addUser']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      declarations: [ AddUserComponent ],
      providers: [
        {provide: UsersService, useValue: usersServiceSpy},
        {provide: Router, useValue: routerSpy}
      ],
      imports [ ReactiveFormsModule ]
    })
    .compileComponents();

  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    usersService = TestBed.get(UsersService);
  });

  it('should submit user form', () => {
    expect(component).toBeTruthy();
    component.userForm.name = 'name';
    component.userForm.email = 'email@mail.com';
    component.userForm.description = 'some description';

    fixture.detectChanges();

    const el = fixture.nativeElement.querySelector('button');
    el.click();
    fixture.detectChanges();
    expect(usersService.addUser.calls.count()).toBe(1, 'one call');
  });

});
