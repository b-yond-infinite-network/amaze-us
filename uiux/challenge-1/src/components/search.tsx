import * as React from 'react';

import SearchBar from './search-bar';
import Artists from './artists';
import { Grid } from '@material-ui/core';
import Empty from './empty';

export default class Search extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            artists: null
        };
    }

    setArtists(artists) {
        this.setState({
            artists: artists,
            searched: true
        });
    }

    render() {
        return (
            <Grid container spacing={16}>
                <Grid item xs={12}>
                    <SearchBar setArtists={this.setArtists.bind(this)} />
                </Grid>
                {
                    this.state['searched'] ?
                        this.state['artists'] && this.state['artists'].length > 0 ?
                            <Artists artists={this.state['artists']} /> : <Empty message='No artist' />
                        : ''
                }
            </Grid>
        );
    }
}