import { setStep } from './userStepsActions';
import { SET_ARTIST_NAME, GET_ARTISTS, SET_SONG_INFO, SET_LYRICS_INFO } from './artistsActionsConstants.js'

const API_KEY= "d493d92075ff98f97a725d8d1213a6de";
const BASE_URL="http://api.musixmatch.com/ws/1.1/";

export function setArtistName(newValue) {
    console.log(newValue);
    return {
        type: SET_ARTIST_NAME,
        payload: newValue
    }
}

export function setArtistList(artistData) {
    return {
        type: GET_ARTISTS,
        payload: artistData
    }
}

export function setSongInfo(songInfoData) {
    return {
        type: SET_SONG_INFO,
        payload: songInfoData
    }
}

export function setLyricsInfo(lyricsData) {
    return {
        type: SET_LYRICS_INFO,
        payload: lyricsData.lyrics_body
    }
}

export function apiRequest () {
    return (dispatch, getState) => {
        var artistName = getState().artists.artistName || "Justin Bieber"; 

        fetch(`${BASE_URL}artist.search?q_artist=${artistName}&format=json&apikey=${API_KEY}`)
        .then(response => response.json())
        .then(data => {
            dispatch(setArtistList(data.message.body.artist_list));
            dispatch(setStep("results"));
        });
    }
}

export function searchForTracksByArtist (artistId) {
    return dispatch => {
        fetch(`${BASE_URL}track.search?q_artist=${artistId}&format=json&apikey=${API_KEY}`)
        .then(response => response.json())
        .then(data => {
            // this.setState({ songInfo: data.message.body.track_list });
            var newData = data.message.body.track_list.filter( (t) => {
                if (!t.track.has_lyrics)
                    return false;
                else
                    return t;
            });

            dispatch(setSongInfo(newData));
            dispatch(setStep("songs"));
        });
    }
}

export function getLyrics (trackId) {
    return dispatch => {
        fetch(`${BASE_URL}track.lyrics.get?track_id=${trackId}&format=json&apikey=${API_KEY}`)
        .then(response => response.json())
        .then(data => {
          dispatch(setLyricsInfo(data.message.body.lyrics));
          dispatch(setStep("playsong"));
        });
    }
}