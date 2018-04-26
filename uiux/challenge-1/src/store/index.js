// Libraries
import {initStore} from 'react-waterfall';
// Services
import MusixmatchProvider from 'api/musixmatch/provider';
import {
  artistAdapter,
  trackAdapter,
  lyricsAdapter
} from 'api/musixmatch/adapters';

// @todo: move this hardcoded value out of here
const musixmatchProvider = new MusixmatchProvider({
  apiKey: '4148caebc14fa40fdc1b7fa3b3aced63'
});

const initialState = {
  artistQuery: '',
  searchArtistResults: [],
  selectedArtist: null,
  searchTracksByArtistResults: [],
  selectedTrack: null,
  selectedTrackLyrics: null
};

export default initStore({
  initialState,
  actions: {

    /**
     * @method reset
     * @description Clear evertything
     */
    reset: () => initialState,

    /**
     * @method searchArtist
     */
    searchArtist: async (state, query) => {
      const results = await musixmatchProvider.searchArtist(query);
      return {
        artistQuery: query.q_artist,
        searchArtistResults: results.artist_list.map(artist => artistAdapter(artist))
      };
    },

    /**
     * @method selectArtist
     */
    selectArtist: async (state, artist) => {
      const results = await musixmatchProvider.searchTracksByArtist({
        f_artist_id: artist.id
      });
      return {
        selectedArtist: artist,
        searchTracksByArtistResults: results.track_list.map(track => trackAdapter(track))
      };
    },

    /**
     * @method getTrackLyrics
     */
    getTrackLyrics: async (state, track) => {
      const lyrics = await musixmatchProvider.getTrackLyrics({
        track_id: track.id
      });
      return {
        selectedTrack: track,
        selectedTrackLyrics: lyricsAdapter(lyrics)
      };
    }
  }
});