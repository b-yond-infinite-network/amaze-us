import React, { Component } from 'react';
import { Panel } from 'react-bootstrap';
import { connect } from 'react-redux';

// constants
class SongPanel extends Component {
    render(props) {
        return (
            this.props.userStep === "playsong" &&
            <Panel>
                <h1 style={ {color: 'orange'} }>Lyrics</h1>
                <div key={ this.props.song }>
                    <label>
                    { this.props.song }
                    </label>
                </div>
            </Panel>
        )
    }
}

const mapStateToProps = (state, props) => {
    return {
        song: state.artists.song,
        userStep: state.userSteps.step,
    }
};
  
const mapActionsToProps = {

};

export default connect(mapStateToProps, mapActionsToProps)(SongPanel);