import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class TracksService {

    constructor(private http: HttpClient) {
    }

    getTracks(artistName: string, page: number, orderByField: string, ascending: boolean): Observable<Track[]> {
        return this.http.get<Track[]>(encodeURI(`/api/track/${artistName}`), {
            params: {
                page: `${page}`,
                order_by: `${orderByField}`,
                ascending: `${ascending}`
            }
        });
    }

    getLyrics(trackId: number): Observable<Lyrics> {
        return this.http.get<Lyrics>(encodeURI(`/api/lyrics/${trackId}`));
    }
}

export class Track {
    id: number;
    title: string;
    artistName: string;
    length: number;
}

export class Lyrics {
    id: number;
    text: string;
    track: Track;
}