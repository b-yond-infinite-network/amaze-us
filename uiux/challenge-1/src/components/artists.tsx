import * as React from 'react';

import Tracks from './tracks';
import Artist from '../models/artist';

export default class Artists extends React.Component<{ artists }> {
    artists: Artist[];

    constructor(props) {
        super(props)

        this.state = {
            artist: null
        };
    }

    async showTracks(id) {
        let artist;
        for (let i = 0; i < this.artists.length; ++i) {
            if (this.artists[i].id === id) {
                artist = this.artists[i];
                break;
            }
        }

        return artist.getTracks().then(() => {
            this.setState({ artist });
        });
    }

    setArtists() {
        this.artists = [];
        for (let i = 0; i < this.props.artists.length; ++i) {
            const a = this.props.artists[i];
            this.artists.push(new Artist(a.artist));
        }
    }

    render() {
        this.setArtists();

        const list = this.artists.map(artist => {
            return <div key={artist.id} className='artist' onClick={this.showTracks.bind(this, artist.id)}>
                {
                    artist.name.length > 75 ?
                        artist.name.substr(0, 75) + '...' : artist.name
                }
            </div>
        });
        
        return <div className="row">
            <div className="col-md-4 col-sm-4">
                <div className="box">
                    <h2>Artists</h2>
                    {list}
                </div>
            </div>
            <div className="col-md-8 col-sm-8">
            {
                this.state['artist'] ?
                    <Tracks tracks={this.state['artist'].tracks} sorted={[]} /> : ''
            }
            </div>
        </div>;
    }
}
