import React, { useState, useEffect } from "react";
import userService from "../services/UserService";
import { Redirect } from "react-router-dom";

export const LoginPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [messageStyle, setMessageStyle] = useState("text-primary");

  useEffect(() => {
    document.title = "Bus Drivers Shifts - Login";
  }, []);
  // const history = useHistory();

  let user = userService.getCurrentUser();
  const isAuthenticated = user ? true : false;

  if (isAuthenticated) return <Redirect to="/" />;

  return (
    <div className="row mt-4">
      <div className="col-sm-4 col-2"></div>

      <div className="card col-sm-4 col-8 text-center">
        <div className="card-body">
          <h6 className="h6">Please Login!</h6>
          {message && (
            <div className={messageStyle}>
              <span>{message}</span>
            </div>
          )}
          <input
            type="text"
            className="form-control mt-1"
            id="username"
            aria-describedby="usernameHelp"
            placeholder="Enter username here"
            value={username}
            onChange={e => {
              setUsername(e.target.value);
              setMessage("");
            }}
          />
          <input
            type="password"
            className="form-control mt-1"
            id="password"
            placeholder="Enter password here"
            value={password}
            onChange={e => {
              setPassword(e.target.value);
              setMessage("");
            }}
          />
          <button
            className="btn btn-primary mt-1"
            onClick={() => {
              if (username === "" || password === "") {
                setPassword("");
                setMessageStyle("text-danger");
                setMessage("Invalid input");
              } else {
                // setMessageStyle("text-primary");
                // setMessage(
                //   "Username = " + username + ", Password = " + password
                // );

                let resp = userService.login(username, password);

                resp
                  .then(response => {
                    if (response.data) {
                      localStorage.setItem(
                        "user",
                        JSON.stringify(response.data)
                      );
                    }
                    // history.push("/");
                    window.location.reload();
                  })
                  .catch(err => {
                    if (err.response && err.response.data) {
                      // alert(
                      //   `${err.response.data.message} (code: ${err.response.data.code})`
                      // );
                      setMessage(
                        `${err.response.data.message} (code: ${err.response.data.code})`
                      );
                      setMessageStyle("text-danger");
                    } else {
                      setMessage("Error, please contact administration");
                      setMessageStyle("text-danger");
                    }
                  });
              }
            }}
          >
            Login
          </button>
        </div>
      </div>
      <div className="col-sm-4 col-2"></div>
    </div>
  );
};
