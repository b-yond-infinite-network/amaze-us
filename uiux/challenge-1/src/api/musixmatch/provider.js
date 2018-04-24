// Utils
import {
  fetchHandler,
  requiredParam,
  serializeUrlParams
} from 'util/index.js';

const defaults = {
  format: 'json',
  url: 'https://api.musixmatch.com/ws/1.1/'
};

/**
 * @method extractResponseBody
 * @description Unpack data from most musixmatch APIs
 * @param {Promise} promise
 * @returns {Object}
 */
const extractResponseBody = promise => promise
  .then(response => response.message.body);

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
   * @method getTrackLyrics
   * @returns {Promise}
   */
  getTrackLyrics = ({
    track_id = requiredParam('track_id')
  } = {}) => {
    const route = this.getRoute({
      method: 'track.lyrics.get',
      params: {
        track_id
      }
    });

    let promise = fetch(route);
    promise = fetchHandler(promise);
    promise = extractResponseBody(promise);
    return promise;
  }

  /**
   * @method searchArtist
   * @returns {Promise}
   */
  searchArtist = ({
    q_artist = requiredParam('q_artist'),
    page_size = 6
  } = {}) => {
    const route = this.getRoute({
      method: 'artist.search',
      params: {
        q_artist,
        page_size
      }
    });

    let promise = fetch(route);
    promise = fetchHandler(promise);
    promise = extractResponseBody(promise);
    return promise;
  }

  /**
   * @method searchTracksByArtist
   * @returns {Promise}
   */
  searchTracksByArtist = ({
    f_artist_id = requiredParam('f_artist_id'),
    page_size = 12
  } = {}) => {
    const route = this.getRoute({
      method: 'track.search',
      params: {
        f_artist_id,
        s_track_rating: 'desc',
        page_size
      }
    });

    let promise = fetch(route);
    promise = fetchHandler(promise);
    promise = extractResponseBody(promise);
    return promise;
  }

  // /**
  //  * @method getAlbumsByArtist
  //  * @returns {Promise}
  //  */
  // getAlbumsByArtist = (artist_id = requiredParam('artist_id')) => {
  //   const route = this.getRoute({
  //     method: 'artist.albums.get',
  //     params: {
  //       artist_id
  //     }
  //   });
  //   return fetchHandler(fetch(route));
  // }

  // /**
  //  * @method getAlbum
  //  * @returns {Promise}
  //  */
  // getAlbum = (album_id = requiredParam('album_id')) => {
  //   const route = this.getRoute({
  //     method: 'album.get',
  //     params: {
  //       album_id
  //     }
  //   });
  //   return fetchHandler(fetch(route));
  // }

  // /**
  //  * @method getTracksByAlbum
  //  * @returns {Promise}
  //  */
  // getTracksByAlbum = (album_id = requiredParam('album_id')) => {
  //   const route = this.getRoute({
  //     method: 'album.tracks.get',
  //     params: {
  //       album_id,
  //       f_has_lyrics: true,
  //       page_size: 100
  //     }
  //   });
  //   return fetchHandler(fetch(route));
  // }

  // /**
  //  * @method getTrack
  //  * @returns {Promise}
  //  */
  // getTrack = (track_id = requiredParam('track_id')) => {
  //   const route = this.getRoute({
  //     method: 'track.get',
  //     params: {
  //       track_id
  //     }
  //   });
  //   return fetchHandler(fetch(route));
  // }


  
};

export default MusixmatchProvider;