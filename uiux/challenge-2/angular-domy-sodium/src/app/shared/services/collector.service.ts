import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CollectorService {

  constructor(
    private http: HttpClient
  ) { }

  getFoodCollector(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/v1/collector/food_level`);
  }

  getWaterCollector(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/v1/collector/water_level`);
  }

}
