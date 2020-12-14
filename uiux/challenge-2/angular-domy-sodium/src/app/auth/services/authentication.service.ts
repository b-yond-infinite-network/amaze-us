import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

interface UserParams {
  recognitionNumber: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private http: HttpClient
  ) { }

  login(params: UserParams): Observable<any> {
    return this.http.post<any>(`${'http://localhost:3000'}/v1/auth/authenticate`,
      { recognition_number: params.recognitionNumber, password: params.password }, {});
  }

  checkPreRegisterUser(recognition_number: string): Observable<{ [k:string]: boolean }> {
    return this.http.get<any>(`${'http://localhost:3000'}/v1/pioneer/user_pre_registration/${recognition_number}`, {})
  }

}
