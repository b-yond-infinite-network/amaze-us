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

  changeSelectedTrack = (newTrack) => {
    const { selectedTrackId } = this.state;
    this.setState({
      selectedTrackId: newTrack === selectedTrackId ? undefined : newTrack
    })
  } 

  render() {
    const { tracks, changeSortAttribute, sortedBy } = this.props;
    const { selectedTrackId } = this.state;
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
            onClick={this.changeSelectedTrack}
            showLyrics={selectedTrackId === track.track_id} 
          />
        ))}
      </div>
    )
  }
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
