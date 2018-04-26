// Libraries
import React from 'react';

const TrackList = ({
  actions,
  tracks
}) => {

  /**
   * @method selectTrack
   * @description Dispatch user selection
   */
  const selectTrack = trackId => actions.getTrackLyrics(trackId);

  /**
   * @method onClick
   * @description Handle link click
   */
  const onClick = (event, trackData) => {
    event.preventDefault();
    selectTrack(trackData); 
  };

  return (
    <ul>
      {tracks
        .map(track => (
          <li
            key={track.id}
          >
            <a
              href="#0"
              onClick={event => onClick(event, track)}
            >{track.name}</a> {track.formattedDuration}
          </li>
        ))
      }
    </ul>
  );
};

export default TrackList;