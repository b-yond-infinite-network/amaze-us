import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { TrackModel } from '../models/track.model';

@Injectable({
  providedIn: 'root'
})
export class MusixmatchService {

  baseUri: string = environment.API_URL;
  token: string = environment.API_KEY;

  constructor(private http: HttpClient) { }

  // Track search by Artist Name
  getTracksByArtist(aName): Observable<any> {
    const url = `${this.baseUri}track.search?format=jsonp&q_artist=${aName}&apikey=${this.token}&page_size=100`;
    return this.http.jsonp(url, 'callback').pipe(
      map((res: any) => {
        if (res.message.body.track_list.length) {
          return res.message.body.track_list.map(val => <TrackModel> {
            album_name: val.track.album_name,
            updated_time: val.track.updated_time,
            artist_name: val.track.artist_name,
            track_id : val.track.track_id,
            track_name : val.track.track_name,
            has_lyrics : val.track.has_lyrics
          });
        } else {
          return [];
        }
    }), catchError(err => this.errorHandler('getTracksByArtist', err) ));
  }

  // Get Lyrics By Track
  getLyricsByTrack(tId): Observable<any> {
    const url = `${this.baseUri}track.lyrics.get?format=jsonp&track_id=${tId}&apikey=${this.token}`;
    return this.http.jsonp(url, 'callback').pipe(
      map((res: any) => {
        if (res.message.body.lyrics) {
          return res.message.body.lyrics.lyrics_body;
        } else {
          return '';
        }
    }), catchError(err => this.errorHandler('getLyricsByTrack', err) ));
  }

   // Error handling
   errorHandler(errorFlow, errorMessage) {
    let errorResonse = '';
    errorResonse = `Error instance: ${errorFlow}\nMessage: ${errorMessage}`;
    console.log(errorResonse);
    return throwError(errorResonse);
  }
}
