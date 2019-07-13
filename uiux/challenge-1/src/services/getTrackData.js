import { AxiosInstance } from './AxiosInstance';
import { pick } from 'lodash';

const trackAttributes = ['track_name', 'artist_name', 'track_id', 'lyrics', 'track_rating', 'wordCount'];
const apiKey = '064b9c9b7cfedab8d404802c855976cc';

const getTrackData = async (currentArtist) => {
  const searchUrl = `track.search?q_artist=${currentArtist}&page_size=10&f_has_lyrics=true&s_track_rating=desc&apikey=${apiKey}`;

  const tracksResult = await AxiosInstance.get(searchUrl);
  const tracks = await tracksResult.data.message.body.track_list;

  for (const trackObject of tracks) {
    // Need to do this old style loop in order to get the axios calls to work.
    const trackId = trackObject.track.track_id;
    const lyricsResult = await AxiosInstance.get(`track.lyrics.get?track_id=${trackId}&apikey=${apiKey}`)
    const lyrics = await lyricsResult.data.message.body.lyrics.lyrics_body;
    trackObject.track.lyrics = lyrics;
  }
  // It has this weird track.track structure. So, in order to make things cleaner:
  const flattened = tracks.map(track => {
    const realTrack = track.track;
    realTrack.wordCount = realTrack.lyrics
      ? realTrack.lyrics.split(' ').length 
      : 0;
      return pick(realTrack, trackAttributes);
  });
  return flattened;
}

export default getTrackData;