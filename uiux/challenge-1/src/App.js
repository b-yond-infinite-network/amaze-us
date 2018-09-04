// Author: Anthony Guevara
// E-mail: anthony.guev@gmail.com
// Date:  May 27, 2018 - version 1.0.0 - Initial working app
//        September 3, 2018 - version 2.0.0 - Refactor App.js render
//            function
//            - Add redux state management
// Challenge 1 - Karaoke needs words

import React, { Component } from 'react';
import './App.css';
import Navbar from './components/Navbar'
import SearchHeader from './components/SearchHeader'
import FormTextField from './components/FormTextField'
import SongPanel from './components/SongPanel'
import ArtistPanel from './components/ArtistPanel'
import SongListPanel from './components/SongListPanel'

// bootstrap-react
import { FormGroup, Grid } from 'react-bootstrap';
// put any other imports below so that CSS from your
// components takes precedence over default styles.
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-theme.css';

// react table
import 'react-table/react-table.css';

// redux
import { connect } from 'react-redux';

// main app
class App extends Component {

  render() {
    // css
    var fullScreen = {
      height: '100vh',
      background: 'linear-gradient(to right, #1a2a6c, #b21f1f, #fdbb2d)'
    }

    return (
        <div style={ fullScreen }>
          <Navbar />
          <Grid>
            <SearchHeader />
            <FormGroup controlId="artistSearch">
              <FormTextField />
            </FormGroup>
            <ArtistPanel/>
            <SongListPanel/>
            <SongPanel/>
          </Grid>
        </div>
    );
  }
}

const mapStateToProps = (state, props) => {
  return {
  }
};

const mapActionsToProps = {
};

export default connect(mapStateToProps, mapActionsToProps)(App);
