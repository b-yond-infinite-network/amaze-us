import * as React from 'react';

const Artist = props => {
    const list = props.artists.map(o => {
        return <li data-artist-id={o.artist.artist_id}>{o.artist.artist_name}</li>
    });
    return <ul>{list}</ul>;
}
export default Artist;
