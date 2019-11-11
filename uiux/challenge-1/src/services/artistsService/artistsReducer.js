import {
    GET_ARTISTS_LIST_REQUEST,
    GET_ARTISTS_LIST_SUCCESS,
    GET_ARTISTS_LIST_ERROR,
} from './artistsActionTypes';

export default (state = {}, action) => {
    switch (action.type) {
        case GET_ARTISTS_LIST_REQUEST:
            return {
                ...state,
                isLoading: true,
            }
        case GET_ARTISTS_LIST_SUCCESS:
            return {
                ...state,
                isLoading: false,
                artistList: action.artistList,
            }
        case GET_ARTISTS_LIST_ERROR:
            return {
                ...state,
                isLoading: false,
                errors: action.errors,
            }
        default:
            return state;
    }
}
