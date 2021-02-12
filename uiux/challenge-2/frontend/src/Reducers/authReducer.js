import {LOGIN_FAIL, LOGIN_SUCCESS, LOGOUT,} from "../Actions/types";


const auth = JSON.parse(localStorage.getItem("auth"));

const initialState = auth && auth.user && auth.token ? {isLoggedIn: true, auth} : {
  isLoggedIn: false,
  user: null,
  token: null
};

export default function authReducer(state = initialState, action) {
  const {type, payload} = action;

  switch (type) {
    case LOGIN_SUCCESS:
      return {
        ...state,
        isLoggedIn: true,
        token: payload.token,
        user: payload.user
      };
    case LOGIN_FAIL:
      return {
        ...state,
        isLoggedIn: false,
        token: null,
        user: null
      };
    case LOGOUT:
      return {
        ...state,
        isLoggedIn: false,
        token: null,
        user: null
      };
    default:
      return state;
  }
}
