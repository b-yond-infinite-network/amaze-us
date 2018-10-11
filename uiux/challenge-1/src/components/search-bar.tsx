import * as React from 'react';
import { musicmatchdata, searchArtist } from '../services/musicmatch';

export default class Search extends React.Component<{ setArtists }> {
    constructor(props) {
        super(props);
        this.state = { search: '' };
    }

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
            <div className="row">
                <div className="col-xs-12">
                    <div className="box box-container">
                        <div className="row">
                            <div className="col-xs-12">
                                <input type="text" className="search-field" onKeyPress={this.onKeyPress.bind(this)} onChange={this.updateSearch.bind(this)} placeholder="Find your artist" />
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-xs-12 center-xs">
                                <button className="search-button" onClick={this.search.bind(this)}>Find it</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
