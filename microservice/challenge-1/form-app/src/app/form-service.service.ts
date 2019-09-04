import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

import { FormModel } from './form-model';

@Injectable({
  providedIn: 'root'
})
export class FormServiceService {

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) { }

  public getFormData(): Observable<Array<FormModel>>{
    return this.http.get<Array<FormModel>>('http://localhost:65107/getdata')
  }

  public saveFormData(formModel: FormModel): Observable<any>{
    return this.http.post<FormModel>('http://localhost:65365/save', formModel);

  }

}
