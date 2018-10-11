import * as React from 'react';

import Track from '../models/track';

const ASC = 'asc';
const DESC = 'desc';

const DEFAULT_SORT = {
    name: null,
    length: null
};

export default class Tracks extends React.Component<{ sorted: Track[],tracks:Track[] }> {
    sort: object;

    constructor(props) {
        super(props);

        this.sort = {...DEFAULT_SORT};

        this.state = {
            lyrics: null
        };
    }

    getSortCaret(column) {
        let up = 'fas fa-caret-up', down = 'fas fa-caret-down';
        if (this.sort[column] !== null) {
            if (this.sort[column] === ASC) {
                down += ' white'
            } else {
                up += ' white';
            }
        }
        return <div className='carets'>
            <i className={up}></i>
            <i className={down}></i>
        </div>;
    }

    showLyrics(track) {
        track.getLyric().then(lyrics => {
            const l = lyrics.replace(/[\r\n]/g, '<br>');
            this.setState({ lyrics: l });
        })
    }

    sortBy(column) {
        const goAsc = this.sort[column] === DESC || this.sort[column] === null;
        this.sort = { ...DEFAULT_SORT };
        this.sort[column] = !goAsc ? DESC : ASC;
        this.props['tracks'].sort((a, b) => {
            if (goAsc) {
                if (a[column] > b[column]) return 1;
                if (a[column] < b[column]) return -1;
                return 0;
            } else {
                if (a[column] < b[column]) return 1;
                if (a[column] > b[column]) return -1;
                return 0;
            }
        });
        this.setState({});
    }

    render() {
        return (
            <div className='box'>
                <table className='tracks-list'>
                    <thead>
                        <tr>
                            <th className='track-name' onClick={this.sortBy.bind(this, 'name')}>Track name {this.getSortCaret('name')}</th>
                            <th className='track-length' onClick={this.sortBy.bind(this, 'length')}>length {this.getSortCaret('length')}</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.props['tracks'].length > 0 ?
                                this.props['tracks'].map(track => {
                                    if (track.has_lyrics) {
                                        return <tr key={track.id} onClick={this.showLyrics.bind(this, track)}>
                                            <td>{
                                                track.name.length > 40 ? track.name.substr(0, 40) + '...' : track.name
                                            }</td>
                                            <td>{track.length}</td>
                                        </tr>
                                    }
                                })
                                : <tr><td colSpan={3}>No tracks</td></tr>
                        }
                    </tbody>
                </table>
                {
                    this.state['lyrics'] ? 
                        <div className='lyrics container-fluid'>
                            <div className='col-xs-12'>
                                <div className='box' dangerouslySetInnerHTML={{ __html: this.state['lyrics'] }}></div>
                            </div>
                        </div> : ''
                }
                
            </div>
        );
    }
};