import React, { Component } from 'react';
import { connect } from 'react-redux';
import { searchForTracksByArtist } from '../redux/actions/artistsActions';

class ArtistResults extends Component {
    render(props) {
        return (
            this.props.artists.map(a =>
                <div key={ a.artist.artist_id }>
                  <a onClick={ () => this.props.onSearchForTracksByArtist(a.artist.artist_name) }>{ a.artist.artist_name }</a>
                </div>
              )
        )
    }
}

const mapStateToProps = (state, props) => {
    return {
        artists: state.artists.artistInfo,
    }
};
  
const mapActionsToProps = {
    onSearchForTracksByArtist: searchForTracksByArtist,
};

export default connect(mapStateToProps, mapActionsToProps)(ArtistResults);