import Artist from "../../types/artist";
import Track, { Lyrics } from "../../types/track";

const ArtistsResponse: Artist[] = [
  { artist: { artist_id: 1, artist_name: "foo" } },
  { artist: { artist_id: 2, artist_name: "bar" } }
];

const TracksResponse: Track[] = [
  {
    track: {
      track_id: 1,
      track_name: "first",
      has_lyrics: 1,
      artist_id: 1,
      artist_name: "foo",
      album_name: "Album 1"
    }
  },
  {
    track: {
      track_id: 2,
      track_name: "second",
      has_lyrics: 0,
      artist_id: 2,
      artist_name: "bar",
      album_name: "Album 2"
    }
  }
];

const LyricsResponse: Lyrics = {
  lyrics_body: "hello world",
  explicit: 0,
  lyrics_copyright: "foo",
  lyrics_id: 1
};

const Responses = {
  ARTISTS: { artist_list: ArtistsResponse },
  ARTIST: ArtistsResponse[0],
  TRACKS: { track_list: TracksResponse },
  TRACK: TracksResponse[0],
  LYRICS: { lyrics: LyricsResponse }
};

export function call(url: string) {
  let response: any;

  if (/artist.search/.test(url)) {
    // artists list
    response = Responses.ARTISTS;
  } else if (/artist.get/.test(url)) {
    // tracks by artist
    response = Responses.ARTIST;
  } else if (/track.search/.test(url)) {
    // artist list
    response = Responses.TRACKS;
  } else if (/track.get/.test(url)) {
    // artist list
    response = Responses.TRACK;
  } else if (/track.lyrics.get/.test(url)) {
    // artist list
    response = Responses.LYRICS;
  }

  return Promise.resolve(response);
}
