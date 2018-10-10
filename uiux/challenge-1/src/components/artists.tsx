import * as React from 'react';
import Artist from '../models/artist';

export default class Artists extends React.Component<{ artists }> {
    artists: Artist[];

    constructor(data) {
        super(data)

        this.artists = [];
        for (let i = 0; i < data.artists.length; ++i) {
            const a = data.artists[i];
            this.artists.push(new Artist(a.artist));
        }
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

    render() {
        const list = this.artists.map(artist => {
            return <div key={artist.id} onClick={this.showTracks.bind(this, artist.id)}>
                {artist.name}
            </div>
        });
        let tracks = '';
        if (this.state['artist'] && this.state['artist'].tracks && this.state['artist'].tracks.length > 0) {
            tracks = this.state['artist'].tracks.map(track => {
                return <div key={track.id}>
                    {track.name}
                </div>
            });
        }
        return <div>{list}
            {
                tracks ? <div className="track-list">{tracks}</div>: ''
            }
        </div>;
    }
}
