import { client } from '.';
import { MAX_SEARCH_RESULTS } from '../constants';

export const searchArtist = (query, limit = MAX_SEARCH_RESULTS) => client.get('artist.search', {
  q_artist: query,
  page_size: limit,
});

export const searchTrack = (artistId, query, limit = MAX_SEARCH_RESULTS) => client.get('track.search', {
  f_artist_id: artistId,
  q_track: query,
  page_size: limit,
  s_track_rating: 'desc',
  f_has_lyrics: 1,
});

export const getLyrics = trackId => client.get('track.lyrics.get', {
  track_id: trackId,
});
