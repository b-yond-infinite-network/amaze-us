import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CollectorService {

  constructor(
    private http: HttpClient
  ) { }

  getFoodCollector(): Observable<any> {
    return this.http.get<any>(`${'http://localhost:3000'}/v1/collector/food_level`);
  }

  getWaterCollector(): Observable<any> {
    return this.http.get<any>(`${'http://localhost:3000'}/v1/collector/water_level`);
  }

}
