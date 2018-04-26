// Libraries
import React from 'react';

const TrackView = ({
  actions,
  lyrics,
  track
}) => {

  /**
   * @method selectTrack
   * @description Dispatch user selection
   */
  const reset = () => actions.reset();

  /**
   * @method onClick
   * @description Handle link click
   */
  const onClick = (event) => {
    event.preventDefault();
    reset(); 
  };
  
  return (
    <div>
      <h4>{track.name}</h4>
      <p>{track.album.name}</p>
      <p>{lyrics.lyrics}</p>

      <a
        href="#0"
        onClick={event => onClick(event)}
      >Start over</a>
    </div>
  );
};

export default TrackView;