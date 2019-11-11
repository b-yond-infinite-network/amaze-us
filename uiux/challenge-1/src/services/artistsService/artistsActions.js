import {
    GET_ARTISTS_LIST_REQUEST,
    GET_ARTISTS_LIST_SUCCESS,
    GET_ARTISTS_LIST_ERROR,
} from './artistsActionTypes';

export const getArtistsList = (keyword) => {
    return {
        type: GET_ARTISTS_LIST_REQUEST,
        keyword,
    }
}

export const getArtistsListSuccess = (artistList) => {
    return {
        type: GET_ARTISTS_LIST_SUCCESS,
        artistList
    }
}

export const getArtistsListError = (errors) => {
    return {
        type: GET_ARTISTS_LIST_ERROR,
        errors
    }
}