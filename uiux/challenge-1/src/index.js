import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from 'components/app';

import MusixmatchProvider from 'api/musixmatch-provider';

const musixmatchProvider = new MusixmatchProvider({
  apiKey: '4148caebc14fa40fdc1b7fa3b3aced63'
});

(async () => {
  const artistInfo = await musixmatchProvider.searchArtist('the black keys');
  console.log('artists', artistInfo.message.body.artist_list);
  const artist = artistInfo.message.body.artist_list[0].artist;

  const albums = await musixmatchProvider.getAlbumsByArtist(artist.artist_id);
  console.log('albums', albums.message.body.album_list);

  const album = albums.message.body.album_list[0].album;
  console.log('album', album);

  const albumData = await musixmatchProvider.getAlbum(album.album_id);
  console.log('albumData', albumData.message.body.album);

  const tracks = await musixmatchProvider.getTracksByAlbum(album.album_id);
  console.log('tracks', tracks.message.body.track_list);

  const track = tracks.message.body.track_list[0].track;
  console.log('track', track);

  const trackData = await musixmatchProvider.getTrack(track.track_id);
  console.log('trackData', trackData.message.body.track);

  const trackLyrics = await musixmatchProvider.getTrackLyrics(track.track_id);
  console.log('trackLyrics', trackLyrics.message.body.lyrics);
  console.log(trackLyrics.message.body.lyrics.lyrics_body);

  const tracksByArtist = await musixmatchProvider.searchTracksByArtist(artist.artist_id);
  console.log(tracksByArtist.message.body.track_list);
  tracksByArtist.message.body.track_list
    .map(item => console.log(item.track.track_name, item.track));
})();


ReactDOM.render(<App />, document.getElementById('root'));