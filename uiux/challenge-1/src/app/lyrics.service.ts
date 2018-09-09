import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
const apiKey = `eabb6c5bfbb14f41ae0a64cb9a279292`;

const headers = new HttpHeaders();
headers.append('Access-Control-Allow-Headers', 'Content-Type');
headers.append('Access-Control-Allow-Methods', 'GET');
headers.append('Access-Control-Allow-Origin', '*');

@Injectable({
    providedIn: 'root'
})



export class LyricsService {
   
    constructor(private _http: HttpClient) {

    }

    getLyrics(trackId) {
        console.log('inside get lyrics method');
        var lyricsUrl =`https://api.musixmatch.com/ws/1.1/track.lyrics.get?track_id=${trackId}&apikey=${apiKey}`;
        return this._http.get(lyricsUrl,{headers});
    }
}