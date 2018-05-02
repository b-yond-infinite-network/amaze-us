// Libraries
import React, { Component } from 'react';
import debounce from 'lodash/debounce';
// Utils
import {
  DEBOUNCE_TIMEOUT,
  MINIMUM_CHARACTER_INPUT
} from 'util/constants';
// Store
import store from 'store';
// Components
import ArtistList from 'components/presentational/artist-list';

const {
  actions,
  connect
} = store;

class ArtistSearch extends Component {

  constructor (props) {
    super(props);
    this.onSubmit = this.onSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
    this.triggerArtistQuery = debounce(this.triggerArtistQuery.bind(this), DEBOUNCE_TIMEOUT);
    this.state = {
      artistQuery: this.props.artistQuery
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

    // Clear current selection
    actions.reset(null);

    // Some rudimentary validation
    if(this.state.artistQuery && this.state.artistQuery.length >= MINIMUM_CHARACTER_INPUT){
      actions.searchArtist({
        q_artist: this.state.artistQuery
      });
    }
  };

  componentWillReceiveProps (nextProps) {
    this.setState(state => ({
      artistQuery: nextProps.artistQuery
    }));
  }

  render () {
    return (
      <React.Fragment>
        {!this.props.selectedArtist &&
        <form onSubmit={this.onSubmit}>
          <input
            className="karaoke--artist-search--input"
            onChange={this.onChange}
            placeholder="Gimme an artist"
            type="text"
            value={this.state.artistQuery}
          />
        </form>
        }
        {!this.props.selectedArtist &&
        <ArtistList
          actions={actions}
          artists={this.props.searchArtistResults}
        />
        }
      </React.Fragment>
    );
  }
}

const ConnectedArtistSearch = connect(state => ({
  artistQuery: state.artistQuery,
  searchArtistResults: state.searchArtistResults,
  selectedArtist: state.selectedArtist
}))(ArtistSearch);

export default ConnectedArtistSearch;
