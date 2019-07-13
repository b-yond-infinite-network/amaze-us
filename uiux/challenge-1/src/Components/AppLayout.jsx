import React from 'react';
import { Jumbotron, Button, Input, Row, Col, Spinner } from 'reactstrap';
import { pick, sortBy } from 'lodash';
import TrackList from './TrackList';
import { AxiosInstance } from '../services/AxiosInstance';
import './AppLayout.css';

// These would be in dot.env file.
const trackAttributes = ['track_name', 'track_id', 'lyrics', 'track_rating', 'wordCount'];
const apiKey = '064b9c9b7cfedab8d404802c855976cc';

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

  getSearchResultsAsync = async ()=> {
    this.toggleButtonLoad();
    // Want to break this down or even move it to a different file.
    const { currentArtist } = this.state;
    const searchUrl = `track.search?q_artist=${currentArtist}&page_size=10&f_has_lyrics=true&s_track_rating=desc&apikey=${apiKey}`;

    const tracksResult = await AxiosInstance.get(searchUrl);
    const tracks = await tracksResult.data.message.body.track_list;

    for (const trackObject of tracks) {
      // Need to do this old style loop in order to get the axios calls to work.
      const trackId = trackObject.track.track_id;
      const lyricsResult = await AxiosInstance.get(`track.lyrics.get?track_id=${trackId}&apikey=${apiKey}`)
      const lyrics = await lyricsResult.data.message.body.lyrics.lyrics_body;
      trackObject.track.lyrics = lyrics;
    }
    // It has this weird track.track structure. So, in order to make things cleaner:
    const flattened = tracks.map(track => {
      const realTrack = track.track;
      realTrack.wordCount = realTrack.lyrics
        ? realTrack.lyrics.split(' ').length 
        : 0;
        return pick(realTrack, trackAttributes);
    });
    this.setState({ tracks: flattened }, () => { this.toggleButtonLoad() })
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
            <hr />
            <Input 
              name="artist"
              label="Search Artist"
              value={currentArtist}
              placeholder="ex. Red Hot Chili Peppers"
              onChange={this.onArtistChange}
            />
            <br />
            <Button 
              color="primary" 
              onClick={this.getSearchResultsAsync}
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
            />
          )}
        </Col>
      </Row>
    )
  }
}