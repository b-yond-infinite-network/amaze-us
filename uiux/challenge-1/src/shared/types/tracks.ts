export default interface Track {
  track: {
    track_id: number;
    track_name: string;
    track_rating?: number;
    has_lyrics: number;
    album_name: string;
    artist_id: number;
    artist_name: string;
  };
}
