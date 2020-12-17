import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

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
    return this.http.post<any>(`${environment.apiUrl}/v1/auth/authenticate`,
      { recognition_number: params.recognitionNumber, password: params.password }, {});
  }

  checkPreRegisterUser(recognition_number: string): Observable<{ [k: string]: boolean }> {
    return this.http.get<any>(`${environment.apiUrl}/v1/pioneer/user_pre_registration/${recognition_number}`, {})
  }

  registerConfiramtion(recognition_number: string, password: string): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/v1/auth/register`, { recognition_number, password }, {});
  }

}
