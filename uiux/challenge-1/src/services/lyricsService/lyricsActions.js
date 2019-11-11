import {
    GET_LYRICS_REQUEST,
    GET_LYRICS_SUCCESS,
    GET_LYRICS_ERROR,
} from './lyricsActionTypes';

export const getLyrics = (track_id) => {
    return {
        type: GET_LYRICS_REQUEST,
        track_id,
    }
}

export const getLyricsSuccess = (lyrics) => {
    return {
        type: GET_LYRICS_SUCCESS,
        lyrics
    }
}

export const getLyricsError = (error) => {
    return {
        type: GET_LYRICS_ERROR,
        error
    }
}