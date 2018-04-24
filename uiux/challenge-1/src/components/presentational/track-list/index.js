// Libraries
import React from 'react';

const ResultsList = ({
  actions,
  searchTracksByArtistResults
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
    <div>
      <ul>
        {searchTracksByArtistResults
          .map(track => (
            <li
              key={track.id}
            >
              <a
                href="#0"
                onClick={event => onClick(event, track)}
              >{track.name}</a>
            </li>
          ))
        }
      </ul>
    </div>
  );
};

export default ResultsList;