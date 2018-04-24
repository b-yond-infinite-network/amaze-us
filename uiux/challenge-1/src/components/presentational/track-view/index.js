// Libraries
import React from 'react';

const TrackView = ({
  lyrics,
  track
}) => {
  return (
    <div>
      <h4>{track.name}</h4>
      <p>{track.album.name}</p>
      <p>{lyrics.lyrics}</p>
    </div>
  );
};

export default TrackView;