import axios from "axios";

class AuthService {
  login(username, password) {
    return axios
      .post("/v1/login", {username, password})
      .then((response) => {
        if (response.data.token) {
          localStorage.setItem("auth", JSON.stringify({token: response.data.token, user: username}));
        }
        return response.data;
      });
  };

  logout = () => {
    localStorage.removeItem("auth");
  };

}

export function authHeader() {
  const user = JSON.parse(localStorage.getItem("auth"));

  if (user && user.token) {
    return {Authorization: "Bearer " + user.token};
  } else {
    return {};
  }
}

export default new AuthService()
