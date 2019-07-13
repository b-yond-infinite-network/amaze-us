import React from 'react'
import { Row, Col } from 'reactstrap';

export default function TrackRow(props) {
  const { track, onClick, showLyrics } = props;
  return (
    <React.Fragment>
      <Row className="mb-3 track-row" onClick={() => { onClick(track.track_id) }}>
        <Col sm={6}>{track.track_name}</Col>
        <Col sm={3}>10</Col>
        <Col sm={3}>{track.wordCount ? `${track.wordCount} Words` : 'No Lyrics'}</Col>
      </Row>
      {showLyrics
      && (
        <React.Fragment>
          <hr />
          <div className="lyrics-header mb-1">Lyrics</div>
          <Row className="lyrics-pane">
            <Col>
              {track.lyrics}
            </Col>
          </Row>
          <hr />
        </React.Fragment>
      )}
    </React.Fragment>
  )
}