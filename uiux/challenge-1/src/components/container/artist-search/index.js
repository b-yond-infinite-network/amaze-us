
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import debounce from 'lodash/debounce';
import {DEBOUNCE_TIMEOUT} from 'util/constants';

class ArtistSearch extends Component {

  constructor (props) {
    super(props);
    // this.onSubmit = this.onSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
    this.triggerArtistQuery = debounce(this.triggerArtistQuery.bind(this), DEBOUNCE_TIMEOUT);
    this.state = {
      artistQuery: ''
    };
  }

  // onSubmit (event) {
  //   event.preventDefault();
  //   this.props.handleArtistSearchSubmit(event.target['karaoke-artist-search'].value);
  // };

  triggerArtistQuery () {
    this.props.handleArtistQuery(this.state.artistQuery);
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
            id="karaoke-artist-search"
            name="karaoke-artist-search"
            onChange={this.onChange}
            type="text"
            value={this.state.artistQuery}
          />
          {/*
          <input
            type="submit"
          />
          */}
        </form>
      </div>
    );
  }
}

ArtistSearch.propTypes = {
  handleArtistSearchSubmit: PropTypes.func
};

export default ArtistSearch;
