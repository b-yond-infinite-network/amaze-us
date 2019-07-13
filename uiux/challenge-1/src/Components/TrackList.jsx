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
      <div className="container-fluid">
        <Row>
          <Col className={`header-col ${sortedBy === 'track_name' ? 'underline' : ''}`} onClick={() => changeSortAttribute('track_name')} sm={3}>Track Title</Col>
          <Col className={`header-col ${sortedBy === 'artist_name' ? 'underline' : ''}`} onClick={() => changeSortAttribute('artist_name')}sm={3}>Artist</Col>
          <Col className={`header-col ${sortedBy === 'wordCount' ? 'underline' : ''}`}  onClick={() => changeSortAttribute('wordCount')} sm={3}>Lyrics Length</Col>
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
  })),
  changeSortAttribute: PropTypes.func.isRequired,
  sortedBy: PropTypes.string.isRequired,
};

TrackList.defaultProps = {
  tracks: [],
}
