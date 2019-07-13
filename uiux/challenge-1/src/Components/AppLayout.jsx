import React from 'react';
import { Jumbotron, Button, Input, Row, Col, Spinner } from 'reactstrap';
import { sortBy } from 'lodash';
import TrackList from './TrackList';
import getTrackData from '../services/getTrackData';
import './AppLayout.css';

export default class AppLayout extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      currentArtist: '',
      tracks: [],
      sortedBy: 'track_name',
      isLoading: false,
    }
  }

  onArtistChange = (event) => {
    this.setState({
      currentArtist: event.target.value,
      tracks: [],
    });
  }

  getSearchResults = () => {
    this.toggleButtonLoad();
    const { currentArtist } = this.state;
    getTrackData(currentArtist)
      .then(tracks => {
        this.setState({ tracks }, () => { this.toggleButtonLoad() })
      })
  }

  changeSortAttribute = attribute => {
    this.setState({ sortedBy: attribute })
  }

  toggleButtonLoad = () => {
    this.setState(prevState => ({
      isLoading: !prevState.isLoading,
    }))
  }

  render() {
    const { tracks, sortedBy, isLoading, currentArtist } = this.state;
    const sortedTracks = sortBy(tracks, sortedBy)
    return(
      <Row>
        <Col>
          <Jumbotron>
            <h3>Welcome to my party!</h3>
            <div>To start having some fun, search up the name of an artist below!</div>
            <div>We'll get you the top 10 highest rated songs from that artist.</div>
            <hr />
            <Row>
              <Col sm='5'>
                <Input 
                name="artist"
                label="Search Artist"
                value={currentArtist}
                placeholder="ex. Red Hot Chili Peppers"
                onChange={this.onArtistChange}
                />
              </Col>
            </Row>
            <br />
            <Button 
              color="primary" 
              onClick={this.getSearchResults}
              disabled={isLoading}
            >
              {isLoading ? <Spinner size="sm"/> : 'Search'}
            </Button>
          </Jumbotron>
          <br />
          {(tracks && tracks.length !== 0) 
          && (
            <TrackList 
              tracks={sortedTracks} 
              changeSortAttribute={this.changeSortAttribute} 
              sortedBy={sortedBy}
            />
          )}
        </Col>
      </Row>
    )
  }
}