// Author: Anthony Guevara
// E-mail: anthony.guev@gmail.com
// Date: May 27, 2018
// Challenge 1 - Karaoke needs words

import React, { Component } from 'react';
import './App.css';
import Navbar from './components/Navbar.js'

// bootstrap-react
import { Panel, Badge, FormGroup, FormControl, Grid, Jumbotron, Button } from 'react-bootstrap';
// put any other imports below so that CSS from your
// components takes precedence over default styles.
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-theme.css';

// react table
import ReactTable from "react-table";
import 'react-table/react-table.css'

// constants
const API_KEY= "d493d92075ff98f97a725d8d1213a6de";
const ENTER_CHAR_CODE=13;
const BASE_URL="http://api.musixmatch.com/ws/1.1/";

// main app
class App extends Component {

  constructor() {
    super();
    this.keyDownFunction = this.keyDownFunction.bind(this);
    this.handleClick = this.handleClick.bind(this);
    this.handleArtistNameChange = this.handleArtistNameChange.bind(this);
    this.searchForArtist = this.searchForArtist.bind(this);
    this.searchForTracks = this.searchForTracks.bind(this);

    // initialize states
    this.state = {
      artistName: '',
      userStep: 'search',
      artistInfo: [],
      songInfo: [],
      song: []
    }
  }

  // enter key will initiate search
  keyDownFunction(e) {
    if (e.charCode === ENTER_CHAR_CODE) {
      this.searchForArtist();
    }
  }
  
  handleClick() {
    this.searchForArtist();
  }

  handleArtistNameChange(e) {
    this.setState({ artistName: e.target.value });
  }

  // fetch artist data and update state
  searchForArtist() {
    fetch(`${BASE_URL}artist.search?q_artist=${this.state.artistName}&format=json&apikey=${API_KEY}`)
      .then(response => response.json())
      .then(data => {
        this.setState({ artistInfo: data.message.body.artist_list });
        this.setState({ userStep: 'results' });
      });
  }

  // fetch track data and update state, filter out tracks with no lyrics as they have no use in a karaoke app
  searchForTracks(artistId) {
    fetch(`${BASE_URL}track.search?q_artist=${artistId}&format=json&apikey=${API_KEY}`)
      .then(response => response.json())
      .then(data => {
        this.setState({ songInfo: data.message.body.track_list });
        var newData = data.message.body.track_list.filter( (t) => {
          if (!t.track.has_lyrics)
            return false;
          else
            return t;
        });

        this.setState({ songInfo: newData });
        this.setState({ userStep: 'songs' });
      });
  }

  // fetch lyric data and update state
  getLyrics(trackId) {
    fetch(`${BASE_URL}track.lyrics.get?track_id=${trackId}&format=json&apikey=${API_KEY}`)
      .then(response => response.json())
      .then(data => {
        this.setState({ song: data.message.body.lyrics });
        this.setState({ userStep: 'playsong' });
      });
  }

  render() {
    // table column headers
    const columns = [
      {
        Header: 'Track id',
        show: false,
        accessor: 'track.track_id'
      },
      {
        Header: 'Track title',
        accessor: 'track.track_name',
        Cell: props => <a onClick={ () => this.getLyrics(props.original.track.track_id) }>{ props.value }</a>
      },
      {
        Header: 'Duration (second)',
        accessor: 'track.track_length',
        // format track time from seconds
        Cell: props => {
          var track_length_seconds = props.original.track.track_length;
          var seconds = track_length_seconds % 60;
          var minutes = Math.floor(track_length_seconds / 60);

          if (minutes < 10)
            minutes="0"+minutes

          if (seconds < 10)
            seconds="0"+seconds

          return `${minutes}:${seconds}`;
        }
      }
    ]

    // css
    var fullScreen = {
      height: '100vh',
      background: 'linear-gradient(to right, #1a2a6c, #b21f1f, #fdbb2d)'
    }

    var font = {
      color:'orange'
    }

    return (
      <div style={ fullScreen }>
        <Navbar />
        <Grid>
          <Jumbotron>
            <h1>Enter an artist name below to search for lyrics:</h1>
          </Jumbotron>

          <FormGroup controlId="artistSearch">
            <FormControl
              type="text"
              placeholder= "Justin Bieber"
              onKeyPress={ this.keyDownFunction }
              value={ this.state.artistName }
              onChange={ this.handleArtistNameChange }
            >
            </FormControl>
            <Button 
              type="submit"
              bsStyle="primary"
              onClick={ this.handleClick }>
                Search by artist
            </Button>
          </FormGroup>

          { this.state.userStep === "songs" &&
            <Panel>
              <h1 style={font}>Song Results <Badge>{ this.state.songInfo ? this.state.songInfo.length : 0 }</Badge></h1>
              <ReactTable
                showPageSizeOptions={ false }
                showPagination={ false }
                noDataText="No rows found"
                style={{
                  width: "100%",
                  height: "50%"
                }}
                data={ this.state.songInfo} 
                columns={ columns }
                minRows="0"
              />
            </Panel>
          }
          <Panel>
          {
            this.state.userStep === "results" &&
            <h1 style={font}>Artist Results <Badge>{ this.state.artistInfo ? this.state.artistInfo.length : 0 }</Badge></h1>
          }
          {
            this.state.userStep === "results" && (
                this.state.artistInfo.map(a =>
                  <div key={ a.artist.artist_id }>
                    <a onClick={ () =>this.searchForTracks(a.artist.artist_name) }>{ a.artist.artist_name }</a>
                  </div>
                )
            )
          }
          </Panel>
          {
            this.state.userStep === "playsong" &&
            <Panel>
                <h1 style={ font }>Lyrics</h1>
                <div key={ this.state.song.lyrics_id }>
                    <label>
                      { this.state.song.lyrics_body }
                    </label>
                </div>
            </Panel>
          }
        </Grid>
      </div>
    );
  }
}

export default App;
