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
    <ul style={{
      listStyleType: 'none',
      margin: 0,
      padding: 0
    }}>
      {tracks
        .map((track, index) => (
          <li
            key={track.id}
            style={{
              fontWeight: 'bold',
              margin: '0 0 1rem 0'
            }}
          >
            <a
              href="#0"
              onClick={event => onClick(event, track)}
              style={{
                display: 'block',
              }}
            >
              <span
                style={{
                  background: `rgba(255, 255, 255, ${1 - (index / 12)})`,
                  borderRadius: '100%',
                  display: 'inline-block',
                  height: '4rem',
                  marginRight: '0.5rem',
                  textDecoration: 'none',
                  width: '4rem',
                }}
              ></span>
              <span                
                style={{
                  color: `rgba(255, 255, 255, ${1 - (index / 12)})`,
                  display: 'inline-block',
                  fontSize: '2rem',
                  height: '4rem',
                  lineHeight: '4rem',
                  textDecoration: 'none',
                  verticalAlign: 'top'
                }}>{track.name} <small>{track.formattedDuration}</small></span>
            </a>
          </li>
        ))
      }
    </ul>
  );
};

export default TrackList;