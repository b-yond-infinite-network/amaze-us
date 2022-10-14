import { useEffect, useState } from 'react';
import { getLyrics, searchArtist, searchTrack } from './api/requests';
import { SearchSection } from './components/SearchSection';
import { LyricsView } from './components/LyricsView';

export const App = () => {
  const [artist, setArtist] = useState(null);
  const [track, setTrack] = useState(null);
  const [lyrics, setLyrics] = useState(null);

  useEffect(() => {
    setTrack(null);
    setLyrics(null);
  }, [artist]);

  useEffect(() => {
    if (track) {
      getLyrics(track.track.track_id)
        .then(setLyrics);
    } else {
      setLyrics(null);
    }
  }, [track]);

  return (
    <>
      <SearchSection
        title="Artist"
        onSearch={query => searchArtist(query)
          .then(({ artist_list }) => artist_list.map(a => ({
            key: a.artist.artist_id,
            name: a.artist.artist_name,
            value: a,
          })))}
        setValue={setArtist}
        value={artist}
      />
      {artist !== null && (
        <SearchSection
          key={artist.artist.artist_id}
          title="Track"
          onSearch={query => searchTrack(artist.artist.artist_id, query)
            .then(({ track_list }) => track_list.map(t => ({
              key: t.track.track_id,
              name: t.track.track_name,
              value: t,
            })))}
          setValue={setTrack}
          value={track}
        />
      )}
      {lyrics !== null && (
        <LyricsView
          lyrics={lyrics.lyrics.lyrics_body}
          copyright={lyrics.lyrics.lyrics_copyright}
          trackingUrl={lyrics.lyrics.script_tracking_url}
          artistName={artist?.artist?.artist_name}
          trackName={track?.track?.track_name}
        />
      )}
    </>
  );
};
