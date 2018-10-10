import Lyric from './lyric';

export default class Track {
    id: number;
    album: string;
    name: string;
    length: number;
    has_lyrics: boolean;
    lyric: Lyric;

    constructor(data) {
        this.album = data.album_name;
        this.name = data.track_name;
        this.length = data.track_length;
        this.id = data.track_id;
        this.has_lyrics = data.has_lyrics;
        if (data.has_lyrics)
            this.lyric = new Lyric({ id: data.lyrics_id });
    }
}
