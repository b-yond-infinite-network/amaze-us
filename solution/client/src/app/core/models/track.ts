export class Track {
    id: string;
    name: string;
    album_id: string;
    artist_name: string
    has_lyrics: boolean;
    constructor(obj: any = {}) {
        this.id = obj.id;
        this.name = obj.name;
        this.album_id = obj.album_id;
        this.artist_name = obj.artist_name;
        this.has_lyrics = obj.has_lyrics;
    }
}