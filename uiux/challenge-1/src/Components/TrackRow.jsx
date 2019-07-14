import React from 'react'
import PropTypes from 'prop-types';
import { Row, Col } from 'reactstrap';

export default function TrackRow(props) {
  const { track, onClick, showLyrics } = props;
  return (
    <React.Fragment>
      <Row className="track-row" onClick={() => { onClick(track.track_id) }}>
        <Col>{track.track_name}</Col>
        <Col>{track.artist_name}</Col>
        <Col>{track.wordCount ? `${track.wordCount} Words` : 'No Lyrics'}</Col>
      </Row>
      {showLyrics
      && (
        <React.Fragment>
          <hr />
          <div className="lyrics-header mb-1">Lyrics</div>
          <Row>
            <Col className="lyrics">
              {track.lyrics}
            </Col>
          </Row>
          <hr />
        </React.Fragment>
      )}
    </React.Fragment>
  )
}

TrackRow.propTypes = {
  track: PropTypes.shape({
    track_name: PropTypes.string,
    track_id: PropTypes.number,
    artist_name: PropTypes.string,
    wordCount: PropTypes.number
  }).isRequired,
  showLyrics: PropTypes.bool.isRequired,
  onClick: PropTypes.func.isRequired,
}