import {
  fetchHandler,
  requiredParam,
  serializeUrlParams
} from 'util/index.js';

const defaults = {
  format: 'json',
  url: 'https://api.musixmatch.com/ws/1.1/'
};

class MusixmatchProvider {

  constructor (options) {
    this.options = {
      ...defaults,
      ...options
    };
  }

  /**
   * @method getRoute
   * @description Composes a musixmatch route, including authorization
   * @returns {String}
   */
  getRoute = ({
    method = requiredParam('method'),
    params = {}
  }) => {
    const urlParams = serializeUrlParams({
      apikey: this.options.apiKey,
      format: this.options.format,
      ...params
    });
    return `${this.options.url}${method}?${urlParams}`;
  }

  /**
   * @method searchArtist
   * @returns {Promise}
   */
  searchArtist = (q_artist = requiredParam('q_artist')) => {
    const route = this.getRoute({
      method: 'artist.search',
      params: {
        q_artist
      }
    });
    return fetchHandler(fetch(route));
  }

  /**
   * @method searchTracksByArtist
   * @returns {Promise}
   */
  searchTracksByArtist = (f_artist_id = requiredParam('f_artist_id')) => {
    const route = this.getRoute({
      method: 'track.search',
      params: {
        f_artist_id,
        s_track_rating: 'desc'
      }
    });
    return fetchHandler(fetch(route));
  }

  /**
   * @method getAlbumsByArtist
   * @returns {Promise}
   */
  getAlbumsByArtist = (artist_id = requiredParam('artist_id')) => {
    const route = this.getRoute({
      method: 'artist.albums.get',
      params: {
        artist_id
      }
    });
    return fetchHandler(fetch(route));
  }

  /**
   * @method getAlbum
   * @returns {Promise}
   */
  getAlbum = (album_id = requiredParam('album_id')) => {
    const route = this.getRoute({
      method: 'album.get',
      params: {
        album_id
      }
    });
    return fetchHandler(fetch(route));
  }

  /**
   * @method getTracksByAlbum
   * @returns {Promise}
   */
  getTracksByAlbum = (album_id = requiredParam('album_id')) => {
    const route = this.getRoute({
      method: 'album.tracks.get',
      params: {
        album_id,
        f_has_lyrics: true,
        page_size: 100
      }
    });
    return fetchHandler(fetch(route));
  }

  /**
   * @method getTrack
   * @returns {Promise}
   */
  getTrack = (track_id = requiredParam('track_id')) => {
    const route = this.getRoute({
      method: 'track.get',
      params: {
        track_id
      }
    });
    return fetchHandler(fetch(route));
  }

  /**
   * @method getTrackLyrics
   * @returns {Promise}
   */
  getTrackLyrics = (track_id = requiredParam('track_id')) => {
    const route = this.getRoute({
      method: 'track.lyrics.get',
      params: {
        track_id
      }
    });
    return fetchHandler(fetch(route));
  }
  
};

export default MusixmatchProvider;