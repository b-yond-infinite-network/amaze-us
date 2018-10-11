import Track from './track';
import { searchTracksByArtistId } from '../services/musicmatch';

export default class Artist {
    name: string;
    id: number;
    tracksFetched: boolean;
    tracks: Track[];

    constructor(data) {
        let artist = data;
        if (data.artist) {
            artist = data.artist;
        }
        this.name = artist.artist_name;
        this.id = artist.artist_id;
        this.tracks = [];
        this.tracksFetched = false;
    }
    
    getTracks() {
        if (!this.tracksFetched) {
            return searchTracksByArtistId(this.id).then(results => {
                const tracks = results.message.body.track_list;
    
                this.tracksFetched = true;
                this.addTracks(tracks);
                return this.tracks;
            });
        }
        return Promise.resolve(this.tracks);
    }

    addTrack(data) {
        this.tracks.push(new Track(data.track));
    }

    addTracks(data) {
        for (let i = 0; i < data.length; ++i) {
            this.addTrack(data[i]);
        }
    }
}
