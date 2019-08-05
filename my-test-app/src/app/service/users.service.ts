import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private usersUrl = 'users';

   httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
   };

  constructor(private http: HttpClient) { }

  getUsers():Observable<Array<User>> {
    return this.http.get<Array<User>>(this.usersUrl);
  }

  addUser(user: User): Observable<any> {
    return this.http.post<User>(this.usersUrl, user);
  }

}
