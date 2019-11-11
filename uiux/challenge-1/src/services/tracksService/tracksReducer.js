import {
    GET_TRACKS_LIST_REQUEST,
    GET_TRACKS_LIST_SUCCESS,
    GET_TRACKS_LIST_ERROR,
} from './tracksActionTypes';

export default (state = {}, action) => {
    switch (action.type) {
        case GET_TRACKS_LIST_REQUEST:
            return {
                ...state,
                isLoading: true,
            }
        case GET_TRACKS_LIST_SUCCESS:
            return {
                ...state,
                isLoading: false,
                trackList: action.trackList,
            }
        case GET_TRACKS_LIST_ERROR:
            return {
                ...state,
                isLoading: false,
                error: action.error,
            }
        default:
            return state;
    }
}
