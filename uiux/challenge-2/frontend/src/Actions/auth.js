import {LOGIN_FAIL, LOGIN_SUCCESS, LOGOUT} from "./types";

import AuthService from "../Services/AuthService";

export const login = (username, password) => (dispatch) => {
  return AuthService.login(username, password).then(
    (data) => {
      dispatch({
        type: LOGIN_SUCCESS,
        payload: {
          token: data,
          user: username
        },
      });

      return Promise.resolve();
    },
    (_) => {
      dispatch({
        type: LOGIN_FAIL,
      });
      return Promise.reject();
    }
  );
};

export const logout = () => (dispatch) => {
  AuthService.logout();

  dispatch({
    type: LOGOUT,
  });
};
