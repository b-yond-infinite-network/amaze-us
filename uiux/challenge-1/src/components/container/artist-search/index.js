// Libraries
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import debounce from 'lodash/debounce';
// Utils
import {DEBOUNCE_TIMEOUT} from 'util/constants';
// Store
import store from 'store';
// Components
import ResultsList from 'components/presentational/results-list';

const {
  actions,
  connect
} = store;

const ConnectedResultsList = connect(state => ({
  searchArtistResults: state.searchArtistResults
}))(ResultsList);

class ArtistSearch extends Component {

  constructor (props) {
    super(props);
    this.onSubmit = this.onSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
    this.triggerArtistQuery = debounce(this.triggerArtistQuery.bind(this), DEBOUNCE_TIMEOUT);
    this.state = {
      artistQuery: ''
    };
  }

  onSubmit (event) {
    event.preventDefault();

    // Cancel queued trigger
    this.triggerArtistQuery.cancel();

    // Trigger immediate search
    actions.searchArtist(this.state.artistQuery);
  };

  triggerArtistQuery () {

    // Trigger debounced search
    actions.searchArtist(this.state.artistQuery);
  };

  onChange (event) {

    //cache event value, as the event itself will not be available at the time setState runs
    const artistQuery = event.target.value;

    this.setState(state => ({
      artistQuery
    }));

    this.triggerArtistQuery();
  };

  render() {
    return (
      <div>
        <form onSubmit={this.onSubmit}>
          <label htmlFor="karaoke-artist-search">Artist</label>
          <br/>
          <input
            onChange={this.onChange}
            type="text"
            value={this.state.artistQuery}
          />
        </form>
        <ConnectedResultsList/>
      </div>
    );
  }
}

ArtistSearch.propTypes = {
  handleArtistSearchSubmit: PropTypes.func
};

export default ArtistSearch;
