import Axios from "axios";

const users = "/users";

export const getAll = (params) => Axios.get(users, params);

export const getUser = (id) => Axios.get(users + "/" + id);
