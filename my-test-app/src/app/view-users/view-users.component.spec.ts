import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewUsersComponent } from './view-users.component';
import { UsersService } from '../service/users.service';
import { defer } from 'rxjs';
import { DebugElement } from "@angular/core";
import { By } from "@angular/platform-browser";


export function asyncDataResolved<T>(data: T) {
    return defer(() => Promise.resolve(data));
}

export function asyncDataRejected<T>(data: T) {
    return defer(() => Promise.reject(data));
}

describe('ViewUsersComponent', () => {
  let component: ViewUsersComponent;
  let fixture: ComponentFixture<ViewUsersComponent>;
  let usersService : jasmine.SpyObj<UsersService>;
  let el: DebugElement;

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

    usersService.getUsers.and.returnValue(asyncDataResolved(expectedUsers));
    fixture = TestBed.createComponent(ViewUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    expect(component).toBeTruthy();

    el = fixture.debugElement.query(By.css('tr'));
    expect(el.nativeElement.length).toBe(3);
  });

  it('should show no users available', () => {
    const expectedUsers: Array<User> = [];

    usersService.getUsers.and.returnValue(asyncDataResolved(expectedUsers));
    fixture = TestBed.createComponent(ViewUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    expect(component).toBeTruthy();
    el = fixture.debugElement.query(By.css('h1'));
    expect(el.nativeElement.textContent).toEqual('No data');
  });

  it('should show errors', () => {
    const expectedUsers: Array<User> = [];

    usersService.getUsers.and.returnValue(asyncDataRejected(expectedUsers));
    fixture = TestBed.createComponent(ViewUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    expect(component).toBeTruthy();
    el = fixture.debugElement.query(By.css('h2'));
    expect(el.nativeElement.textContent).toEqual('Error occured. See Logs.');
  });

});
