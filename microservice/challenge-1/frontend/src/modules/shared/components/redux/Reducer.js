import { LOADING_ON, LOADING_OFF } from "./Actions";

const initialState = {
    loading: false,
}

function reducer(state = initialState, action) {
    switch (action.type) {
        case LOADING_ON: {
            return {
                ...state,
                loading: true,
            }
        }

        case LOADING_OFF: {
            let loading = false

            return {
                ...state,
                loading: loading,
            }
        }

        default:
            return state
    }
}

export default reducer
