import { Injectable, OnInit, Inject } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { pluck } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { Md5 } from 'ts-md5/dist/md5';
import {MatSnackBar} from '@angular/material/snack-bar';

import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';

@Injectable({
  providedIn: 'root'
})
export class MusixmatchService {

  private storageKey = 'lyrics_search_cache';
  private results$ = new Subject();

  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService, 
    private http: HttpClient,
    private snackBar: MatSnackBar) {}

  subscribe(subscriberFn) {
    return this.results$.subscribe(subscriberFn);
  }

  search(term: string = '') {
    if (!term || term === '') {
      return;
    }

    if (this.isTermInCache(term)) {
      console.log('Cached result...')
      
      const data = this.getCachedTerm(term);
      this.results$.next(data.data);
      return;
    }

    const termHash = this.hashSearchTerm(term);
    const apikey = 'fcf949768093a50b3c8603f003b6d3ea';
    const url = `http://api.musixmatch.com/ws/1.1/track.search?format=jsonp&callback=callback&q=${term}&s_track_rating=desc&apikey=${apikey}`;

    // Getting the data from the api, and trims the data object
    this.http.jsonp(url, 'callback').pipe(
      pluck('message', 'body')
    ).subscribe(
      (data: any) => {

        // Remove extra track key
        data = data.track_list.map( d => d.track );

        this.cacheSearchTerm(term, data);
        this.results$.next(data);
      },
      (e: HttpErrorResponse) => this.snackBar.open('Ups... something went wrong!', 'Try again later')
    );
  }

  private hashSearchTerm(term: string): string {
    return Md5.hashStr(term.toLowerCase()).toString();
  }

  private getCachedTerm(term: string = ''){
    if (!term || term === '') {
      return [];
    }

    const termHash = this.hashSearchTerm(term);
    const cacheObj = this.storage.get(this.storageKey) || [];
    const cachedValue = cacheObj.find(obj => {
      return obj.termHash === termHash
    })

    return cachedValue || [];
  }

  private isTermInCache(term: string = ''): boolean {

    if (!term || term === '') {
      return false;
    }

    const termHash = this.hashSearchTerm(term);
    const cacheObj = this.storage.get(this.storageKey) || [];
    const cachedValue = cacheObj.find(obj => {
      return obj.termHash === termHash
    })

    if (cachedValue) {
      return true;
    }

    return false;
  }

  private cacheSearchTerm(term, data) {
    const storage = this.storage;
    const termHash = this.hashSearchTerm(term);
    const cacheObj = storage.get(this.storageKey) || [];

    let searchObj = {
      termHash: termHash,
      data: data
    };

    cacheObj.push(searchObj);
    storage.set(this.storageKey, cacheObj);
  }
}
