export const artistAdapter = ({artist}) => ({
  id: artist.artist_id,
  name: artist.artist_name,
  __original: artist
});

export const trackAdapter = ({track}) => ({
  album: {
    name: track.album_name
  },
  duration: track.track_length,
  id: track.track_id,
  name: track.track_name,
  formattedDuration: (track_length => {
    const minutes = Math.floor(track_length / 60);
    const seconds = String('00' + (track_length % 60)).slice(-2);
    return `${minutes}:${seconds}`;
  })(track.track_length),
  rating: track.track_rating,
  wordCount: track.track_name.split(' ').length,
  __original: track
});

export const lyricsAdapter = ({lyrics}) => ({
  lyrics: lyrics.lyrics_body,
  wordCount: lyrics.lyrics_body.split(' ').length,
  __original: lyrics
});