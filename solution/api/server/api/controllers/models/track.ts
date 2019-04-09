
export default class Track {

  id: number;
  name: string;
  rating: string;
  has_lyrics: number;
  album_id: number;
  artist_name: string;
  artist_id: number

  constructor(obj) {
    this.id = obj.track_id;
    this.name = obj.track_name;
    this.has_lyrics = obj.has_lyrics;
    this.artist_name = obj.artist_name;
    this.artist_id = obj.artist_id;
    this.album_id = obj.album_id;
  }
}
