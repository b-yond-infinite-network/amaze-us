import React from 'react';
import { Jumbotron, Button, Input, Row, Col } from 'reactstrap';
import { pick, sortBy, forEach } from 'lodash';
import TrackList from './TrackList';
import { AxiosInstance } from '../services/AxiosInstance';

const trackAttributes = ['track_name', 'track_id', 'lyrics'];
const apiKey = '064b9c9b7cfedab8d404802c855976cc';

export default class AppLayout extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      currentArtist: '',
    }
  }

  onArtistChange = (event) => {
    this.setState({
      currentArtist: event.target.value,
    });
  }

  getSearchResults = () => {
    const { currentArtist } = this.state;
    const searchUrl = `track.search?q_artist=${currentArtist}&page_size=10&f_has_lyrics=true&apikey=${apiKey}`;
    AxiosInstance.get(searchUrl)
      .then(response => {
        const trackList = response.data.message.body.track_list;
        const tracks = trackList.map(track => pick(track.track, trackAttributes));
      })
  }

  getLyrics = (tracks) => {
    const calls = tracks.map(track => (AxiosInstance.get(`track.lyrics.get?track_id=${track.track_id}&apikey=${apiKey}`)));
    return Promise.all(calls)
  }

  getSearchResultsAsync = async ()=> {
    const { currentArtist } = this.state;
    const searchUrl = `track.search?q_artist=${currentArtist}&page_size=10&f_has_lyrics=true&apikey=${apiKey}`;

    const tracksResult = await AxiosInstance.get(searchUrl);
    const tracks = await tracksResult.data.message.body.track_list;

    await tracks.forEach(async trackObject => {
      const trackId = trackObject.track.track_id;
      const lyricsResult = await AxiosInstance.get(`track.lyrics.get?track_id=${trackId}&apikey=${apiKey}`)
      const lyrics = await lyricsResult.data.message.body.lyrics.lyrics_body;
      trackObject.track.lyrics = lyrics;
    })
    this.setState({
      tracks
    })
  }

  render() {
    const { tracks } = this.state;
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
              value={this.state.currentArtist}
              placeholder="ex. Red Hot Chili Peppers"
              onChange={this.onArtistChange}
            />
            <br />
            <Button color="primary" onClick={this.getSearchResultsAsync}>Search!</Button>
          </Jumbotron>
          <br />
          {(tracks && tracks.length) 
          && (
            <TrackList tracks={tracks}/>
          )}
        </Col>
      </Row>
    )
  }
}