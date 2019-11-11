import {
    GET_LYRICS_REQUEST,
    GET_LYRICS_SUCCESS,
    GET_LYRICS_ERROR,
} from './lyricsActionTypes';

export default (state = {}, action) => {
    switch (action.type) {
        case GET_LYRICS_REQUEST:
            return {
                ...state,
                isLoading: true,
            }
        case GET_LYRICS_SUCCESS:
            return {
                ...state,
                isLoading: false,
                lyrics: action.lyrics,
            }
        case GET_LYRICS_ERROR:
            return {
                ...state,
                isLoading: false,
                error: action.error,
            }
        default:
            return state;
    }
}
