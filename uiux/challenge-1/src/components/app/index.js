import React, { Component } from 'react';
import 'components/app/index.css';

import ArtistSearch from 'components/container/artist-search';


class App extends Component {

  constructor (props) {
    super(props);
    this.handleArtistQuery = this.handleArtistQuery.bind(this);
  }

  async handleArtistQuery (query) {
    const searchArtistResults = await this.props.musixmatchProvider.searchArtist(query);
    console.log('searchArtistResults', searchArtistResults);
  }

  render () {
    return (
      <div className="App">
        <ArtistSearch
          handleArtistQuery={this.handleArtistQuery}
        />
      </div>
    );
  }
}

export default App;