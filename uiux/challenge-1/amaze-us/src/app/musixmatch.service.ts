import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { pluck } from 'rxjs/operators';
import { Subject } from 'rxjs';

import { Md5 } from 'ts-md5/dist/md5';

@Injectable({
  providedIn: 'root'
})
export class MusixmatchService {

  constructor(private http: HttpClient) {
    this.results$.subscribe(data => console.log(data));
  }

  results$ = new Subject();

  subscribe(subscriber) {
    return this.results$.subscribe(subscriber);
  }

  search(term: string = '') {

    if (!term || term === '') {
      return;
    }

    const API_KEY = 'fcf949768093a50b3c8603f003b6d3ea';
    let url = `http://api.musixmatch.com/ws/1.1/track.search?format=jsonp&callback=callback&q=${term}&s_track_rating=desc&apikey=${API_KEY}`;

    this.http.jsonp(url, 'callback').pipe(
      pluck('message', 'body', 'track_list')
    ).subscribe(
      (data: any) => {
        this.results$.next(data);
      },
      (e: HttpErrorResponse) => console.log(e)
    );
  }

  private isTermInCache(term:string = ''){

    if(!term || term === ''){
      return false;
    }

    let termHash = Md5.hashStr(term.toLowerCase());

    return true;
  }
}
