import * as React from 'react';

import ArtistCard from './artist-card';
import Artist from '../models/artist';
import { Grid } from '@material-ui/core';

export default class Artists extends React.Component<{ artists: MusicMatchArtist[] }> {
    artists: Artist[];

    setArtists() {
        this.artists = [];
        for (let i = 0; i < this.props.artists.length; ++i) {
            const a: MusicMatchArtist = this.props.artists[i];
            this.artists.push(new Artist(a.artist));
        }
    }

    render() {
        this.setArtists();

        const list = this.artists.map(artist => {
            if (artist)
                return <ArtistCard key={artist.id} artist={artist}></ArtistCard>;
        });
        
        return (
            <Grid container style={{ marginLeft: 16 }}>
                {list}
            </Grid>
        );
    }
}
