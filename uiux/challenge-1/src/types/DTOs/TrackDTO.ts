export interface TrackGetResponse {
    message: Message;
}
export interface Message {
    header: Header;
    body: Body;
}
export interface Header {
    status_code: number;
    execute_time: number;
}
export interface Body {
    track: Track;
}
export interface Track {
    track_id: number;
    track_name: string;
    track_name_translation_list?: (null)[] | null;
    track_rating: number;
    commontrack_id: number;
    instrumental: number;
    explicit: number;
    has_lyrics: number;
    has_subtitles: number;
    has_richsync: number;
    num_favourite: number;
    album_id: number;
    album_name: string;
    artist_id: number;
    artist_name: string;
    track_share_url: string;
    track_edit_url: string;
    restricted: number;
    updated_time: string;
    primary_genres: PrimaryGenres;
}
export interface PrimaryGenres {
    music_genre_list?: (MusicGenreListEntity)[] | null;
}
export interface MusicGenreListEntity {
    music_genre: MusicGenre;
}
export interface MusicGenre {
    music_genre_id: number;
    music_genre_parent_id: number;
    music_genre_name: string;
    music_genre_name_extended: string;
    music_genre_vanity: string;
}
