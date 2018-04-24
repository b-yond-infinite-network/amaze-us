// Libraries
import React, { Component } from 'react';
import debounce from 'lodash/debounce';
// Utils
import {DEBOUNCE_TIMEOUT} from 'util/constants';
// Store
import store from 'store';
// Components
import ArtistList from 'components/presentational/artist-list';

const {
  actions,
  connect
} = store;

const ConnectedArtistList = connect(state => ({
  searchArtistResults: state.searchArtistResults
}))(ArtistList);

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

  /**
   * @method onChange
   * @description Handle search text field input
   */
  onChange (event) {

    //cache event value, as the event itself will not be available at the time setState runs
    const artistQuery = event.target.value;

    this.setState(state => ({
      artistQuery
    }));

    this.triggerArtistQuery();
  };

  /**
   * @method onSubmit
   * @description Handle form submission
   */
  onSubmit (event) {
    event.preventDefault();

    // Cancel queued trigger
    this.triggerArtistQuery.cancel();

    this.triggerArtistQuery();
  };

  /**
   * @method triggerArtistQuery
   * @description Trigger artist search
   */
  triggerArtistQuery () {
    actions.searchArtist({
      q_artist: this.state.artistQuery
    });
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
        <ConnectedArtistList/>
      </div>
    );
  }
}

export default ArtistSearch;
