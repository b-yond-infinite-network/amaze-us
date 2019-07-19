import React from 'react'
import PropTypes from 'prop-types';
import { Row, Col } from 'reactstrap';

const TrackRow = (props) => {
  const { track, onClick, showLyrics, classes } = props;
  return (
    <React.Fragment>
      <Row className={classes.trackRow} onClick={() => { onClick(track.track_id) }}>
        <Col>{track.track_name}</Col>
        <Col>{track.artist_name}</Col>
        <Col>{track.wordCount ? `${track.wordCount} Words` : 'No Lyrics'}</Col>
      </Row>
      {showLyrics
      && (
        <React.Fragment>
          <hr />
          <div className={classes.lyricsHeader}>Lyrics</div>
          <Row>
            <Col className={classes.lyrics}>
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

export default TrackRow;