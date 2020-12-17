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
export class PlantationService {

  constructor(
    private http: HttpClient
  ) { }

  getAllPlantations(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/v1/plantation/farm_active_plantation`);
  }

  createPlantation(params: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/v1/plantation/add_plantation`, params, {})
  }

}
