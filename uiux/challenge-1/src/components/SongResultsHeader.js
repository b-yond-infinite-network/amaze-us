import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Badge } from 'react-bootstrap';

// constants
class SongPanel extends Component {
    render(props) {
        return (
            <h1 style={ {color:'orange'} }>Song Results <Badge>{ this.props.songInfo ? this.props.songInfo.length : 0 }</Badge></h1>
        )
    }
}

const mapStateToProps = (state, props) => {
    return {
        songInfo: state.artists.songInfo,
    }
};
  
const mapActionsToProps = {

};

export default connect(mapStateToProps, mapActionsToProps)(SongPanel);