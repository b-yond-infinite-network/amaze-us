import { Injectable, OnInit, Inject } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { pluck, defaultIfEmpty } from 'rxjs/operators';
import { Subject, BehaviorSubject } from 'rxjs';
import { Md5 } from 'ts-md5/dist/md5';
import { MatSnackBar } from '@angular/material/snack-bar';

import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';

@Injectable({
  providedIn: 'root'
})
export class MusixmatchService {

  private storageKey = 'lyrics_search_cache';
  private results$ = new Subject();
  private isSearching$ = new BehaviorSubject(false);
  private lyric$ = new Subject();
  private isGettingLyric$ = new BehaviorSubject(false);
  private apiKey = 'fcf949768093a50b3c8603f003b6d3ea';

  constructor(@Inject(LOCAL_STORAGE) private storage: StorageService,
    private http: HttpClient,
    private snackBar: MatSnackBar) { }

  subscribe(subscriberFn) {
    return this.results$.subscribe(subscriberFn);
  }

  subscribeLyric(subscriberFn) {
    return this.lyric$.subscribe(subscriberFn);
  }

  isSearching(subscriberFn) {
    return this.isSearching$.subscribe(subscriberFn);
  }

  isGettingLyric(subscriberFn) {
    return this.isGettingLyric$.subscribe(subscriberFn);
  }

  getLyricByTrackId(trackId) {

    // Notify that we are grabbing information
    this.isGettingLyric$.next(true);

    const url = `http://api.musixmatch.com/ws/1.1/track.lyrics.get?format=jsonp&callback=callback&track_id=${trackId}&apikey=${this.apiKey}`;

    this.http.jsonp(url, 'callback').pipe(
      pluck('message', 'body', 'lyrics')
    ).subscribe(
      (data: any) => {
        this.lyric$.next(data);
        this.isGettingLyric$.next(false);
      },
      (e: HttpErrorResponse) => {
        this.isGettingLyric$.next(false);
        this.snackBar.open('Ups... something went wrong!', 'Try again later');
      }
    );
  }

  search(term: string = '') {
    if (!term || term === '' || term.trim() === '') {
      return;
    }

    // Trim white spaces
    term = term.trim();

    // Notify that we are searching
    this.isSearching$.next(true);

    // Seek for the term in the cache and return it if found
    let cachedTerm = this.getCachedTerm(term);

    if (cachedTerm !== null) {
      this.snackBar.open('Displaying cached values', 'Ok', { duration: 1500 });

      this.results$.next(cachedTerm.data);
      this.isSearching$.next(false);
      return;
    }

    // Hash search term to use it as storage key
    const termHash = this.hashSearchTerm(term);
    const url = `http://api.musixmatch.com/ws/1.1/track.search?format=jsonp&callback=callback&q=${term}&s_track_rating=desc&apikey=${this.apiKey}`;

    // Getting the data from the api, and trims the data object
    this.http.jsonp(url, 'callback').pipe(
      pluck('message', 'body')
    ).subscribe(
      (data: any) => {

        // Remove extra track key
        data = data.track_list.map(d => d.track);

        this.cacheSearchTerm(term, data);
        this.isSearching$.next(false);
        this.results$.next(data);
      },
      (e: HttpErrorResponse) => {
        this.isSearching$.next(false);
        this.snackBar.open('Ups... something went wrong!', 'Try again later');
      }
    );
  }

  // A common method to hash a search term
  private hashSearchTerm(term: string): string {
    return Md5.hashStr(term.toLowerCase()).toString();
  }

  // Gets the cached search terms
  private getCachedTerm(term: string = '') {
    if (!term || term === '') {
      return [];
    }

    const termHash = this.hashSearchTerm(term);
    const cacheObj = this.storage.get(this.storageKey) || [];
    const cachedValue = cacheObj.find(obj => {
      return obj.termHash === termHash
    })

    return cachedValue || null;
  }

  // Caches a search term.
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
