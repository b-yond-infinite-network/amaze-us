import * as React from 'react';

import { withStyles } from '@material-ui/core/styles';

import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Tracks from './tracks';

import Artist from '../models/artist';

const StyledCard = withStyles({
    root: {
        display: 'flex',
        flexDirection: 'row',
        maxWidth: 350,
        minWidth: 100,
        margin: 6
    }
})(Card);

const ArtistContent = withStyles({
    root: {
        backgroundColor: '#303030',
        padding: 6,
        paddingBottom: '6px !important',
        width: '100%'
    }
})(CardContent);

const ArtistName = withStyles({
    root: {
        fontSize: 18,
        width: '100%',
        '&:hover': {
            color: '#FFF'
        }
    }
})(Typography);

export default class ArtistCard extends React.Component<{ artist: Artist }> {
    state = { open: false };
    async openModal() {
        return this.props.artist.getTracks().then(() => {
            this.setState({ open: true });
        });
    }

    closeModal() {
        this.setState({ open: false });
    }

    render() {
        const artist = this.props.artist;
        return (
            <StyledCard key={this.props.artist.id} onClick={this.openModal.bind(this)}>
                <ArtistContent>
                    <ArtistName component="p" title={artist.name} variant='h5'>
                        {
                            artist.name.length > 60 ? `${artist.name.substr(0, 60)}...` : artist.name
                        }
                    </ArtistName>
                </ArtistContent>
                <Tracks tracks={this.props.artist.tracks} closeModal={this.closeModal.bind(this)} open={this.state.open} />
            </StyledCard>
        );
    }
}
