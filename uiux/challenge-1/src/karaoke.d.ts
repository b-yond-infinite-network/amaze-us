interface EventTarget {
    value: any;
}

interface Event {
    which: number
}

interface SearchState {
    artists: MusicMatchArtist[],
    searched: boolean
}

interface TrackState {
    order: 'asc' | 'desc';
    orderBy: string;
    lyrics: string;
    rowsPerPage: number;
    showLyrics: boolean;
    page: number;
    trackName: string;
}

// MusicMatch type definition

interface MusicMatchTrackData {
    has_lyrics: boolean;
    album_name: string;
    track_name: string;
    track_length: number;
    track_id: number;
}

interface MusicMatchTrack {
    track: MusicMatchTrackData;
}

interface MusicMatchArtistData {
    artist_name: string;
    artist_id: number;
}

interface MusicMatchArtist {
    artist: MusicMatchArtistData;
}