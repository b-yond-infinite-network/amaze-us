// Libraries
import React from 'react';
// Store
import store from 'store';
// Components
import SortableTrackList from 'components/presentational/sortable-track-list';
import TrackView from 'components/presentational/track-view';

const {
  actions,
  connect
} = store;

/**
 * @method reset
 * @description Reset app
 */
const reset = () => actions.reset();

/**
 * @method onClick
 * @description Handle link click
 */
const onResetClick = (event) => {
  event.preventDefault();
  reset(); 
};

const ArtistView = ({
  searchTracksByArtistResults,
  selectedArtist,
  selectedTrack,
  selectedTrackLyrics
}) => {
  if (selectedArtist) {
    return (
      <React.Fragment>
        {!selectedTrack &&
        <SortableTrackList
          actions={actions}
          tracks={searchTracksByArtistResults}
        />
        }
        {selectedTrackLyrics &&
          <TrackView
            lyrics={selectedTrackLyrics}
            track={selectedTrack}
          />
        }
        <a
          className="ui-pill"
          href="#0"
          onClick={event => onResetClick(event)}
          style={{
            display: 'inline-block',
            position: 'fixed',
            top: '.5em',
            right: 0
          }}
        >Start over <i className="fas fa-undo-alt"></i></a>
      </React.Fragment>
    );
  } else {
    return null;
  }
};

const ConnectedArtistView = connect(state => ({
  searchTracksByArtistResults: state.searchTracksByArtistResults,
  selectedArtist: state.selectedArtist,
  selectedTrack: state.selectedTrack,
  selectedTrackLyrics: state.selectedTrackLyrics
}))(ArtistView);

export default ConnectedArtistView;
