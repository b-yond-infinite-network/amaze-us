import * as React from 'react';

import withStyles from '@material-ui/core/styles/withStyles';
import Button from '@material-ui/core/Button';
import Input from '@material-ui/core/Input';

import { musicmatchdata, searchArtist } from '../services/musicmatch';
import { Grid } from '@material-ui/core';

const SearchBox = withStyles({
    root: {
        color: '#FFF',
        fontSize: 24,
        margin: 'dense'
    },
    underline: { 
        '&:before': {
            borderBottomColor: '#FFF'
        }
    }
})(Input);

const SearchButton = withStyles({
    root: {
        textAlign: 'center'
    }
})(Button);

export default class Search extends React.Component<{ setArtists }> {
    state = { search: '' };

    search() {
        if (!musicmatchdata[this.state['search']]) {
            searchArtist(this.state['search'])
                .then(results => {
                    const artists = results.message.body.artist_list;
                    musicmatchdata[this.state['search']] = artists;
                    this.props.setArtists(artists);
                });
        } else {
            this.props.setArtists(musicmatchdata[this.state['search']]);
        }
    }

    onKeyPress(e) {
        if (e.which === 13) {
            this.search();
        }
    }

    updateSearch(e) {
        this.setState({
            search: e.target.value,
            artists: []
        });
    }

    render() {
        return (
            <Grid container spacing={32}>
                <Grid item xs={10}>
                    <SearchBox color='secondary' fullWidth={true} onKeyPress={this.onKeyPress.bind(this)} onChange={this.updateSearch.bind(this)} placeholder="Find your artist" />
                </Grid>
                <Grid item xs={2}>
                    <SearchButton size='large' color='secondary' variant='contained' onClick={this.search.bind(this)}>Find it</SearchButton>
                </Grid>
            </Grid>
        );
    }
}
