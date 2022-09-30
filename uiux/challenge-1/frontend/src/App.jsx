import { useEffect, useState } from 'react';
import { getLyrics, searchArtist, searchTrack } from './api/requests.js';
import { SearchSection } from './components/SearchSection/index.js';
import { LyricsView } from './components/LyricsView/index.js';

export const App = () => {
  const [artist, setArtist] = useState(null);
  const [track, setTrack] = useState(null);
  const [lyrics, setLyrics] = useState(null);

  useEffect(() => {
    setTrack(null);
    setLyrics(null);
  }, [artist]);

  useEffect(() => {
    if (track)
      getLyrics(track.track.track_id).then(setLyrics);
    else
      setLyrics(null);
  }, [track]);

  return (
    <>
      <SearchSection
        title='Artist'
        onSearch={query => searchArtist(query)
          .then(({ artist_list }) => artist_list.map(artist => ({
            key: artist.artist.artist_id,
            name: artist.artist.artist_name,
            value: artist,
          })))
        }
        setValue={setArtist}
        value={artist}
      />
      {artist !== null && <SearchSection
        key={artist.artist.artist_id}
        title='Track'
        onSearch={query => searchTrack(artist.artist.artist_id, query)
          .then(({ track_list }) => track_list.map(track => ({
            key: track.track.track_id,
            name: track.track.track_name,
            value: track,
          })))}
        setValue={setTrack}
        value={track}
      />}
      {lyrics !== null && <LyricsView
        lyrics={lyrics}
        artistName={artist?.artist?.artist_name}
        trackName={track?.track?.track_name}
      />}
    </>
  );
};
