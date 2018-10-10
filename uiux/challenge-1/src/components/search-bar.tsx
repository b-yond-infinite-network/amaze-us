import * as React from 'react';
import { searchArtist } from '../services/musicmatch';
import Artists from './artists';

export default class Search extends React.Component<{}> {
    constructor() {
        super(null);
        this.state = { search: '' };
    }

    search() {
        searchArtist(this.state['search'])
            .then(results => {
                const artists = results.message.body.artist_list;
                this.setState({ artists: artists });
            });
    }

    updateSearch(e) {
        this.setState({
            search: e.target.value,
            artists: []
        });
    }

    render() {
        return (
            <div>
                <div>
                    <input type="text" onChange={this.updateSearch.bind(this)} placeholder="Find your artist" />
                    <button onClick={this.search.bind(this)}>Find it</button>
                </div>
                {
                    this.state['artists'] && this.state['artists'].length > 0 ? <Artists artists={this.state['artists']} /> : ''
                }
            </div>
        );
    }
}
