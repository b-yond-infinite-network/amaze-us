import React, { useState } from "react";
import userService from "../services/UserService";
import driverService from "../services/DriverService";
import { Redirect } from "react-router-dom";

export const LoginPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [messageStyle, setMessageStyle] = useState("text-primary");

  return (
    <div className="row mt-4">
      <div className="col-4"></div>

      <div className="card col-4 text-center">
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
              let result = driverService.getAllDrivers();
              console.log(`Result = ${result}`);


              if (username === "" || password === "") {
                setPassword("");
                setMessageStyle("text-danger");
                setMessage("Invalid input");
              } else {
                setMessageStyle("text-primary");
                setMessage(
                  "Username = " + username + ", Password = " + password
                );

                userService.login(username, password);
                console.log(
                  'localStorage.getItem("user"): ' +
                    localStorage.getItem("user")
                );

                return <Redirect exact to="/" />;
              }
            }}
          >
            Login
          </button>
        </div>
      </div>
      <div className="col-4"></div>
    </div>
  );
};
