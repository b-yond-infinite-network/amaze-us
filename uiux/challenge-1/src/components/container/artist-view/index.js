// Libraries
import React from 'react';
// Store
import store from 'store';
// Components
import TrackList from 'components/presentational/track-list';
import TrackView from 'components/presentational/track-view';

const {
  connect
} = store;

const ConnectedTrackList = connect(state => ({
  searchTracksByArtistResults: state.searchTracksByArtistResults
}))(TrackList);

const ArtistView = ({
  selectedArtist,
  selectedTrack,
  selectedTrackLyrics
}) => {
  if (selectedArtist) {
    return (
      <React.Fragment>
        <h3>Selected Artist {selectedArtist.name}</h3>
        <h4>Most popular songs</h4>
        <ConnectedTrackList/>
        {selectedTrackLyrics && <TrackView
          track={selectedTrack}
          lyrics={selectedTrackLyrics}
        />}
      </React.Fragment>
    );
  } else {
    return null;
  }
};

const ConnectedArtistView = connect(state => ({
  selectedArtist: state.selectedArtist,
  selectedTrack: state.selectedTrack,
  selectedTrackLyrics: state.selectedTrackLyrics
}))(ArtistView);

export default ConnectedArtistView;
