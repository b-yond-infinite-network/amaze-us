import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { catchError, map } from 'rxjs/operators';
import { Track } from './../models/track'
import { Artist } from './../models/artist'

@Injectable()
export class ApiService {

  constructor(
    private http: HttpClient,
  ) { }

  private logErrors(error: any) {
    return throwError(error.error);
  }

  getLyricsByTrackId(id): Observable<any> {
    return this.http.get(`${environment.api}lyrics/${id}`)
      .pipe(catchError(this.logErrors));
  }

  searchTrack(args: any = {}): Observable<any> {
    const { query, page } = args;
    const params = new HttpParams().set('query', query).set('page', page);
    return this.http.get(`${environment.api}search`, { params })
      .pipe(map((result: any) => {
        let data = result.data && result.data.track.map(val => new Track(val));
        let total = result.data.total
        return { data, total }
      }))
      .pipe(catchError(this.logErrors));
  }

  searchArtist(args: any = {}): Observable<any> {
    const { query, page } = args;
    const params = new HttpParams().set('query', query).set('page', page);
    return this.http.get(`${environment.api}artist`, { params })
      .pipe(map((result: any) => {
        console.log(result)
        let data = result.data && result.data.artists.map(val => new Artist(val));
        let total = result.data.total
        return { data, total }
      }))
      .pipe(catchError(this.logErrors));
  }
}