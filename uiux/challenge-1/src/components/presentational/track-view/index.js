// Libraries
import React from 'react';

const TrackView = ({
  lyrics,
  track
}) => (
  <div>
    <h4
      style={{
        fontSize: '4rem',
        margin: '1rem 0 0 0',
        textShadow: '-.125rem .125rem 0 rgba(0,0,0, .5)'
      }}
    >{track.name}</h4>
    <pre
      style={{
        fontFamily: '\'PT Sans\', sans-serif',
        fontSize: '2rem',
        margin: '1rem 0 0 0'
      }}
    >{lyrics.lyrics}</pre>
  </div>
);

export default TrackView;