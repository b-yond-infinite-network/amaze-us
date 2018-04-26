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

const ArtistView = ({
  searchTracksByArtistResults,
  selectedArtist,
  selectedTrack,
  selectedTrackLyrics
}) => {
  if (selectedArtist) {
    return (
      <React.Fragment>
        <h3>Selected Artist {selectedArtist.name}</h3>
        <h4>Most popular songs</h4>
        <SortableTrackList
          actions={actions}
          tracks={searchTracksByArtistResults}
        />
        {selectedTrackLyrics &&
        <TrackView
          actions={actions}
          lyrics={selectedTrackLyrics}
          track={selectedTrack}
        />
        }
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
