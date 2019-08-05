import{TestBed, inject}from '@angular/core/testing';
import { Observable, of } from 'rxjs';

import {UsersService}from './users.service';
import { HttpClient } from '@angular/common/http';
import { User } from '../model/user';

describe('UsersService', () => {
    let userService: UsersService;
    let httpClientSpy: jasmine.SpyObj<HttpClient>;

    beforeEach(() => {
      const spy = jasmine.createSpyObj('HttpClient', ['get', 'post']);

      TestBed.configureTestingModule({

        providers: [
          UsersService,
          { provide: HttpClient, useValue: spy }
        ]
      });

      userService = TestBed.get(UsersService);
      httpClientSpy = TestBed.get(HttpClient);
    });

  it('should be created', inject([UsersService], (service: UsersService) => {
    expect(service).toBeTruthy();
  }));

  it('should return fetched users', () => {
      const expectedUsers: Array<User> =
        [
            { name:'user1', email:'email1@mail.com', description:'desc1' },
            { name:'user2', email:'email2@mail.com', description:'desc2' },
        ];

      httpClientSpy.get.and.returnValue(of(expectedUsers));

      userService.getUsers().subscribe(
        users => expect(users).toEqual(expectedUsers, 'expected users'),
        fail
      );
      expect(httpClientSpy.get.calls.count()).toBe(1, 'one call');
    });

    it('should post user data', () => {
      let user: User = new User('name', 'email@mail.com', 'description');

      httpClientSpy.post.and.returnValue(of({}));
      userService.addUser(user);
      expect(httpClientSpy.post.calls.count()).toBe(1, 'one call');
    });
});
