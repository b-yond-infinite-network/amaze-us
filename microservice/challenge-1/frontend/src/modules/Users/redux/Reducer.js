import { GET_USERS_OK, GET_USERS_ERROR, GET_USER_OK, GET_USER_ERROR } from './Actions'

const initialState = {
    users: [],
    user: undefined,
    error: undefined
}

function UserReducer(state = initialState, action) {
    switch (action.type) {
        case GET_USERS_OK: {
            return {
                ...state,
                users: action.value
            }
        }
        case GET_USERS_ERROR: {
            return {
                ...state,
                error: action.value
            }
        }
        case GET_USER_OK: {
            return {
                ...state,
                user: action.value
            }
        }
        case GET_USER_ERROR: {
            return {
                ...state,
                error: action.value
            }
        }
        default:
            return state
    }
}

export default UserReducer
