import React from 'react';
import PropTypes from 'prop-types'
import { Row, Col } from 'reactstrap';
import TrackRow from './TrackRow';

export default class TrackList extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedTrackId: undefined,
    }
  }

  changeSelectedTrack = (selectedTrackId) => {
    this.setState({ selectedTrackId })
  } 

  render() {
    const { tracks, changeSortAttribute } = this.props;
    const { selectedTrackId } = this.state;
    return (
      <div className="container">
        <Row>
          <Col className="header-col" onClick={() => changeSortAttribute('track_name')} sm={6}>Track Title</Col>
          <Col className="header-col" sm={3}>Duration</Col>
          <Col className="header-col" onClick={() => changeSortAttribute('wordCount')} sm={3}>Lyrics Length</Col>
        </Row>
        <hr />
        {tracks.map(track => (
          <TrackRow track={track} onClick={this.changeSelectedTrack} showLyrics={selectedTrackId === track.track_id} />
        ))}
      </div>
    )
  }
}

TrackList.propTypes = {
  tracks: PropTypes.arrayOf(PropTypes.shape({
    track_name: PropTypes.string,
    track_id: PropTypes.number,
  })),
  changeSortAttribute: PropTypes.func.isRequired,
};

TrackList.defaultProps = {
  tracks: [],
}
