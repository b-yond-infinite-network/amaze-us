// Libraries
import React, { Component } from 'react';

import {artistAdapter} from 'api/adapters';

class ResultsList extends Component {

  render() {
    return (
      <div>
        <ul>
          {this.props.searchArtistResults
            .map(artist => artistAdapter(artist))
            .map(artist => (
              <li
                key={artist.artistId}
              >{artist.artistName}</li>
            ))
          }
        </ul>
      </div>
    );
  }
}

export default ResultsList;
