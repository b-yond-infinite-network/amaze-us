import { getTrackLyric } from '../services/musicmatch';

export default class Track {
    id: number;
    album: string;
    name: string;
    length: number;
    has_lyrics: boolean;
    lyrics: string;

    constructor(data) {
        this.album = data.album_name;
        this.name = data.track_name;
        this.length = data.track_length;
        this.id = data.track_id;
        this.has_lyrics = data.has_lyrics;
    }

    getLyric() {
        if (!this.lyrics) {
            return getTrackLyric(this.id).then(results => {
                this.lyrics = results.message.body.lyrics.lyrics_body;
                return this.lyrics;
            });
        }
        return Promise.resolve(this.lyrics);
    }
}
