import * as React from 'react';
import { searchArtist } from '../services/musicmatch';

export default class Search extends React.Component<{}> {
    constructor() {
        super(null);
        this.state = { search: '' };
    }

    search() {
        searchArtist(this.state['search'])
            .then(results => {
                const artists = results.message.body.artist_list;
                console.log(artists);
            });
    }

    updateSearch(e) {
        this.setState({
            search: e.target.value
        });
    }

    render() {
        return (
            <div>
                <input type="text" onChange={this.updateSearch.bind(this)} placeholder="Find your artist" />
                <button onClick={this.search.bind(this)}>Find it</button>
            </div>
        );
    }
}
