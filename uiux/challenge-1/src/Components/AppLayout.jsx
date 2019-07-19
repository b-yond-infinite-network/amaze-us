import React from 'react';
import { Jumbotron, Button, Input, Row, Col, Spinner } from 'reactstrap';
import { sortBy, reverse } from 'lodash';
import TrackList from './TrackList';
import getTrackData from '../services/getTrackData';

const AppLayout = ({ classes }) => {
  const [currentArtist, setCurrentArtist] = React.useState('');
  const [tracks, setTracks] = React.useState([]);
  const [sortedBy, setSortedBy] = React.useState('track_name');
  const [isLoading, toggleLoad] = React.useState(false);
  const [sortOrder, setSortOrder] = React.useState('asc');
  const [pages, setPages] = React.useState(0);

  const onArtistChange = (event) => {
    setCurrentArtist(event.target.value);
    setTracks([]);
    setPages(0);
  }

  const onEnterPress = (event) => {
    if (event.key === 'Enter') {
      getSearchResults();
    }
  }

  const getSearchResults = () => {
    toggleLoad(true);
    const nextPage = pages + 1;
    getTrackData(currentArtist, nextPage)
      .then(newTracks => {
        const newList = tracks.concat(newTracks);
        setTracks(newList);
        setPages(nextPage);
        toggleLoad(false);
      })
  }

  const changeSortAttribute = attribute => {
    setSortedBy(attribute);
    setSortOrder(attribute === sortedBy 
      ? sortOrder === 'desc' ? 'asc' : 'desc'
      : 'desc')
  }

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
              onChange={onArtistChange}
              onKeyPress={onEnterPress}
              />
            </Col>
          </Row>
          <br />
          <Button 
            color="primary" 
            onClick={getSearchResults}
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
              changeSortAttribute={changeSortAttribute} 
              sortedBy={sortedBy}
              classes={classes} 
            />
            <Row className={classes.getMoreResultsButton}>
              <Col>
                <Button 
                color="primary" 
                onClick={getSearchResults}
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

export default AppLayout