import React from 'react';
import { Jumbotron, Button, Input, Row, Col, Spinner } from 'reactstrap';
import { sortBy, reverse } from 'lodash';
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
      sortOrder: 'asc'
    }
  }

  onArtistChange = (event) => {
    this.setState({
      currentArtist: event.target.value,
      tracks: [],
      pages: 1,
    });
  }

  getSearchResults = () => {
    this.toggleButtonLoad();
    const { currentArtist, pages, tracks } = this.state;
    getTrackData(currentArtist, pages)
      .then(newTracks => {
        const newList = tracks.concat(newTracks);
        this.setState({ tracks: newList }, () => { this.toggleButtonLoad() })
      })
  }

  getMoreResults = () => {
    this.setState(prevState => ({ pages: prevState.pages + 1 }),
    () => { this.getSearchResults() })
  }

  changeSortAttribute = attribute => {
    this.setState(prevState => ({ 
      sortedBy: attribute,
      sortOrder: attribute === prevState.sortedBy 
        ? prevState.sortOrder === 'desc' ? 'asc' : 'desc'
        : 'desc',
    }));
  }

  toggleButtonLoad = () => {
    this.setState(prevState => ({
      isLoading: !prevState.isLoading,
    }))
  }

  render() {
    const { tracks, sortedBy, isLoading, currentArtist, sortOrder } = this.state;
    const sortedTracks = sortBy(tracks, sortedBy)
    if (sortOrder === 'desc') {
      reverse(sortedTracks);
    }
    return(
      <Row>
        <Col>
          <Jumbotron>
            <h3>Welcome to my party!</h3>
            <div>To start having some fun, search up the name of an artist below!</div>
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
            <React.Fragment>
              <TrackList 
                tracks={sortedTracks} 
                changeSortAttribute={this.changeSortAttribute} 
                sortedBy={sortedBy}
                getMoreResults={this.getMoreResults}
              />
              <Row className="mt-5 text-center">
                <Col>
                  <Button 
                  color="primary" 
                  onClick={this.getMoreResults}
                  disabled={isLoading}
                >
                  {isLoading ? <Spinner size="sm"/> : 'Get more results'}
                </Button>
                </Col>
              </Row>
            </React.Fragment>
          )}
        </Col>
      </Row>
    )
  }
}