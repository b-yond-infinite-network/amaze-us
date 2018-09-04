import React, { Component } from 'react';
import { FormControl } from 'react-bootstrap';
import { apiRequest, setArtistName } from '../redux/actions/artistsActions';
import { connect } from 'react-redux';

// constants
const ENTER_CHAR_CODE=13;

class FormTextField extends Component {
    render(props) {
        return (
            <FormControl
                type="text"
                placeholder= "Justin Bieber (Press enter to search)"
                onKeyPress={ (e) => {
                    if (e.charCode === ENTER_CHAR_CODE) {
                        this.props.onApiRequest();
                    }
                } }
                onChange={ (e) => {
                    this.props.onSetArtistName(e.target.value);
                } }
            />
        )
    }
}

const mapStateToProps = (state, props) => {
    return {
        songInfo: state.artists.songInfo,
    }
};
  
const mapActionsToProps = {
    onApiRequest: apiRequest,
    onSetArtistName: setArtistName,
};

export default connect(mapStateToProps, mapActionsToProps)(FormTextField);