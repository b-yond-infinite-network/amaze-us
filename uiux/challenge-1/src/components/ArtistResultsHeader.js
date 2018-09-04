import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Badge } from 'react-bootstrap';

// constants
class SongPanel extends Component {
    render(props) {
        return (
            <h1 style={ {color:'orange'} }>Artist Results <Badge>{ this.props.artists ? this.props.artists.length : 0 }</Badge></h1>
        )
    }
}

const mapStateToProps = (state, props) => {
    return {
        artists: state.artists.artistInfo,
    }
};
  
const mapActionsToProps = {

};

export default connect(mapStateToProps, mapActionsToProps)(SongPanel);