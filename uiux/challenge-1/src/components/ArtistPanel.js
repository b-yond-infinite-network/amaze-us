import React, { Component } from 'react';
import { Panel } from 'react-bootstrap';
import ArtistResultsHeader from './ArtistResultsHeader';
import ArtistResults from './ArtistResults';
import { connect } from 'react-redux';

class ArtistPanel extends Component {
    render(props) {
        return (
            this.props.userStep === "results" &&
            <Panel>
                <ArtistResultsHeader/>
                <ArtistResults/>
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

export default connect(mapStateToProps, mapActionsToProps)(ArtistPanel);