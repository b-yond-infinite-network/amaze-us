import React from 'react';
import PropTypes from 'prop-types';


const Lyrics = (props) => {
    return (
        <div className="lyrics-wrapper">
            <h2 className="header">Lyrics by {props.artistName}</h2>
            <div className="lyrics-body">
                {props.lyrics}
            </div>
        </div>
    );
}

Lyrics.propTypes = {
    trackName: PropTypes.string.isRequired,
    artistName: PropTypes.string.isRequired,
    lyrics: PropTypes.string.isRequired,
}

export default Lyrics;