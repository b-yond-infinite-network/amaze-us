import React, { useState } from 'react';
import PropTypes from 'prop-types'
import { Row, Col } from 'reactstrap';
import TrackRow from './TrackRow';

const  TrackList = (props) => {

  const [selectedTrackId, setSelectedTrack] = useState(undefined);

  const changeSelectedTrack = (newTrack) => {
    setSelectedTrack(newTrack === selectedTrackId ? undefined : newTrack)
  } 

  const { tracks, changeSortAttribute, sortedBy } = props;
  return (
    <div className="container">
      <Row>
        <Col className={`header-col ${sortedBy === 'track_name' ? 'selected' : ''}`} onClick={() => changeSortAttribute('track_name')}>Track Title</Col>
        <Col className={`header-col ${sortedBy === 'artist_name' ? 'selected' : ''}`} onClick={() => changeSortAttribute('artist_name')}>Artist</Col>
        <Col className={`header-col ${sortedBy === 'wordCount' ? 'selected' : ''}`}  onClick={() => changeSortAttribute('wordCount')}>Lyrics Length</Col>
      </Row>
      <hr />
      {tracks.map(track => (
        <TrackRow 
          track={track}
          key={track.track_id}
          onClick={changeSelectedTrack}
          showLyrics={selectedTrackId === track.track_id} 
        />
      ))}
    </div>
  )
}

TrackList.propTypes = {
  tracks: PropTypes.arrayOf(PropTypes.shape({
    track_name: PropTypes.string,
    track_id: PropTypes.number,
    artist_name: PropTypes.string,
    wordCount: PropTypes.number
  })),
  changeSortAttribute: PropTypes.func.isRequired,
  sortedBy: PropTypes.string.isRequired
};

TrackList.defaultProps = {
  tracks: [],
}

export default TrackList;
