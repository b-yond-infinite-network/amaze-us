import MusixMatch from '../apis/MusixMatch'
import { combineReducers } from 'redux'
import { showLoading, hideLoading } from './ui'

const apiKey = '829eb4b92a37a836cbf42bb131607b0b';


export const requestSongList = artist => dispatch => {
    dispatch(showLoading())
    dispatch(fetchSongs(artist))
}

export const requestLyric = trackID => dispatch => {
    dispatch(showLoading())
    dispatch(getLyric(trackID))
}

export const requestSong = trackID => dispatch => {
    dispatch(showLoading())
    dispatch(getSong(trackID))
}

const getSong = trackID => async dispatch => {
    const response = await MusixMatch.get (`track.get?track_id=${trackID}&apikey=${apiKey}`)
    dispatch({ type: 'GET_TRACK', payload: response });
}; 


const fetchSongs = artist => async dispatch => {
    const pageSize = 99;
    const page = 1;
    const ratingOrder = "desc"
    const response = await MusixMatch.get (`/track.search?q_artist=${artist}&page_size=${pageSize}&f_has_subtitle=1&page=${page}&s_track_rating=${ratingOrder}&apikey=${apiKey}`)
    dispatch({ type: 'FETCH_SONGS', payload: response });
    dispatch(hideLoading());
}; 
  
const getLyric = trackID => async dispatch => {
    const response = await MusixMatch.get (`track.lyrics.get?track_id=${trackID}&apikey=${apiKey}`)
    dispatch({ type: 'GET_LYRIC', payload: response });
    dispatch(hideLoading())
};

const songsReducer = (song = [], action)  => {
    switch (action.type) {
        case 'FETCH_SONGS':
            return action.payload;
        default:
            return song;
    }
};

const lyricReducer = (lyric = [], action)  => {
    switch (action.type) {
        case 'GET_LYRIC':
            return action.payload;
        default:
            return lyric;
    }
};

const trackReducer = (track = [], action)  => {
    switch (action.type) {
        case 'GET_TRACK':
            return action.payload;
        default:
            return track;
    }
};

export const selectSong = song => {
    return{
        type: 'SONG_SELECTED',
        payload: song
    };
};
        
export const fetchArtists = artist => async dispatch => {
    const pageSize = 3;
    const page = 1;

    const response = await MusixMatch.get (`/artist.search?q_artist=${artist}&page_size=${pageSize}&page=${page}&apikey=${apiKey}`)
    dispatch({ type: 'FETCH_ARTISTS', payload: response });
}; 
    
const artistsReducer = (artists = [], action)  => {
    switch (action.type) {
        case 'FETCH_ARTISTS':
            return action.payload.data.message.body.artist_list;
        default:
            return artists;
    }
};


const selectedSongReducer = (selectedSong = null, action) => {
    if (action.type === 'SONG_SELECTED') {
        return action.payload;
    }
    return selectedSong;
};

export default combineReducers ({
    songs: songsReducer,
    selectedSong: selectedSongReducer,
    artists: artistsReducer,
    lyric: lyricReducer,
    track: trackReducer
});