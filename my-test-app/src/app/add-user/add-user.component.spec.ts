import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserComponent } from './add-user.component';
import { Router } from '@angular/router';
import { UsersService } from '../service/users.service';

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
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
