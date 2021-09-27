import axios from "axios";

const REST_API_URL = "http://localhost:8080/bus-drivers-shifts/login";

class UserService {
  // login by username and password
  login = (username, password) => {
    axios.post(
        REST_API_URL,
        {},
        {
          auth: {
            username: username,
            password: password
          }
        }
      )
      .then(response => {
        console.log(response.data);
        if (response.data.accessToken) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }
        return JSON.stringify(response.data);
      })
      .catch(err => console.log(err));
  };
}

export default new UserService();
