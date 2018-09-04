import React, { Component } from 'react';
import { Panel } from 'react-bootstrap';
import SongResultsHeader from './SongResultsHeader';
import ReactTableSimple from './ReactTableSimple';
import { connect } from 'react-redux';

class SongListPanel extends Component {
    render(props) {
        return (
            this.props.userStep === "songs" &&
            <Panel>
                <SongResultsHeader/>
                <ReactTableSimple/>
            </Panel>
        )
    }
}

const mapStateToProps = (state, props) => {
    return {
        userStep: state.userSteps.step,
    }
};
  
const mapActionsToProps = {

};

export default connect(mapStateToProps, mapActionsToProps)(SongListPanel);