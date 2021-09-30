import axios from "axios";

const REST_API_URL = "http://localhost:8080/bus-drivers-shifts/api/user";

class UserService {
  // login by username and password
  login = (username, password) => {
    return axios.post(REST_API_URL, {
      username: username,
      password: password
    });
  };

  logout = () => {
    localStorage.removeItem("user")
  }

  getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"))
  }


}

export default new UserService();
