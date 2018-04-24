export const artistAdapter = ({artist}) => ({
  id: artist.artist_id,
  name: artist.artist_name,
  __original: artist
});

export const trackAdapter = ({track}) => ({
  album: {
    name: track.album_name
  },
  id: track.track_id,
  name: track.track_name,
  __original: track
});

export const lyricsAdapter = ({lyrics}) => ({
  lyrics: lyrics.lyrics_body,
  __original: lyrics
});