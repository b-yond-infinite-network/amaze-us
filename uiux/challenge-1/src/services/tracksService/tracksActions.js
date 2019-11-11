import {
    GET_TRACKS_LIST_REQUEST,
    GET_TRACKS_LIST_SUCCESS,
    GET_TRACKS_LIST_ERROR,
} from './tracksActionTypes';

export const getTracksList = (artist_id) => {
    return {
        type: GET_TRACKS_LIST_REQUEST,
        artist_id,
    }
}

export const getTracksListSuccess = (trackList) => {
    return {
        type: GET_TRACKS_LIST_SUCCESS,
        trackList
    }
}

export const getTracksListError = (errors) => {
    return {
        type: GET_TRACKS_LIST_ERROR,
        errors
    }
}