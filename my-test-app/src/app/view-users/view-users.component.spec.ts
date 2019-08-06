import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewUsersComponent } from './view-users.component';
import { UsersService } from '../service/users.service';
import { of, throwError, Observable } from 'rxjs';
import { DebugElement } from "@angular/core";
import { By } from "@angular/platform-browser";

describe('ViewUsersComponent', () => {
  let component: ViewUsersComponent;
  let fixture: ComponentFixture<ViewUsersComponent>;
  let usersService : jasmine.SpyObj<UsersService>;

  beforeEach(() => {
    const usersServiceSpy = jasmine.createSpyObj('UsersService', ['getUsers']);

    TestBed.configureTestingModule({
      declarations: [ ViewUsersComponent ],
      providers: [
        {provide: UsersService, useValue: usersServiceSpy}
      ]
    })
    .compileComponents();

    usersService = TestBed.get(UsersService);
  });

  it('should render list of all users', () => {
    const expectedUsers: Array<User> =
        [
            { name:'user1', email:'email1@mail.com', description:'desc1' },
            { name:'user2', email:'email2@mail.com', description:'desc2' }
        ];

    usersService.getUsers.and.returnValue(of(expectedUsers));
    fixture = TestBed.createComponent(ViewUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    expect(component).toBeTruthy();
    const el = fixture.nativeElement.querySelectorAll('tr');
    expect(el.length).toBe(3);
  });

  it('should show no users available', () => {
    const expectedUsers: Array<User> = [];

    usersService.getUsers.and.returnValue(of(expectedUsers));
    fixture = TestBed.createComponent(ViewUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    expect(component).toBeTruthy();
    const el = fixture.nativeElement.querySelector('h1');
    expect(el.textContent).toEqual('No data');
  });

  it('should show errors', () => {
    const error: Error = new Error('test error');

    usersService.getUsers.and.returnValue(throwError(error));
    fixture = TestBed.createComponent(ViewUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    expect(component).toBeTruthy();
    const el = fixture.nativeElement.querySelector('h2');
    expect(el.textContent).toEqual('Error occured. See Logs.');
  });

});
