import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
const apiKey = `eabb6c5bfbb14f41ae0a64cb9a279292`;
const pageSize = 25;

const headers = new HttpHeaders();
headers.append('Access-Control-Allow-Headers', 'Content-Type');
headers.append('Access-Control-Allow-Methods', 'GET');
headers.append('Access-Control-Allow-Origin', '*');

@Injectable({
    providedIn: 'root'
})



export class ArtistsService {

    constructor(private _http: HttpClient) {

    }

    getArtists(artistName, sortOrder) {
        var artistsUrl = `https://api.musixmatch.com/ws/1.1/track.search?format=json
                            &q_artist=${artistName}&page_size=${pageSize}
                            &s_track_rating=${sortOrder}&apikey=${apiKey}`;

        return this._http.get(artistsUrl, { headers });
    }
}