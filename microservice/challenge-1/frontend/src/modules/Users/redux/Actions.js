import { getAll, getUser } from "../services/UserServices";

export const GET_USERS_OK = "GET_USERS_OK";
export const GET_USERS_ERROR = "GET_USERS_ERROR";

export const GET_USER_OK = "GET_USER_OK";
export const GET_USER_ERROR = "GET_USER_ERROR";

export function callGetAll() {
  return async function (dispatch) {
    const { data, error } = await getAll();
    if (data) {
      dispatch({
        type: GET_USERS_OK,
        value: data.users,
      });
    } else {
      dispatch({
        type: GET_USERS_ERROR,
        value: error,
      });
    }
  };
}

export function callGetUser(id) {
  return async function (dispatch) {
    const { data, error } = await getUser(id);
    if (data) {
      dispatch({
        type: GET_USER_OK,
        value: data,
      });
    } else {
      dispatch({
        type: GET_USER_ERROR,
        value: error,
      });
    }
  };
}
