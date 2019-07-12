import React from 'react';
import PropTypes from 'prop-types'
import { Row, Col } from 'reactstrap';

export default class TrackList extends React.Component {
  render() {
    const { tracks } = this.props;
    return (
      <div className="container">
        <Row>
          <Col sm={6}>Track Title</Col>
          <Col sm={3}>Duration</Col>
          <Col sm={3}>Lyrics Length</Col>
        </Row>
        <hr />
        {tracks.map(track => (
          <Row className="mb-2">
            <Col sm={6}>{track.track_name}</Col>
            <Col sm={3}>10</Col>
            <Col sm={3}>{track.wordCount ? `${track.wordCount} Words` : 'No Lyrics'}</Col>
          </Row>
        ))}
      </div>
    )
  }
}

TrackList.propTypes = {
  tracks: PropTypes.arrayOf(PropTypes.shape({
    track_name: PropTypes.string,
    track_id: PropTypes.number,
  }))
};

TrackList.defaultProps = {
  tracks: [],
}
