import "./App.css";
import React, { useEffect, useState } from "react";
import { LoginPage } from "./components/LoginPage";
import { MainPage } from "./components/MainPage";
import { Switch, Route } from "react-router-dom";
import userService from "./services/UserService";

function App() {
  return (
    <div>
      <Header />
      <Switch>
        <Route exact path="/" component={MainPage} />
        <Route path="/login" component={LoginPage} />
      </Switch>
    </div>
  );
}

function Header() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  // const [username, setUsername] = useState(null);

  useEffect(() => {
    let user = userService.getCurrentUser();
    setIsAuthenticated(user ? true : false);
    // setUsername(user ? user.username : null);
  }, []);

  return (
    <nav className="navbar navbar-inverse navbar-dark navbar-fixed-top bg-primary">
      <div className="container-fluid">
        <div className="navbar-header">
          <span className="navbar-brand">Bus Drivers Shifts</span>
        </div>
        {isAuthenticated && (
          <ul className="nav navbar-nav navbar-right">
            <li>
              <button
                className="btn btn-danger btn-sm"
                onClick={() => {
                  console.log("logout is clicked!");
                  userService.logout();
                  window.location.reload();
                }}
              >
                Logout
              </button>
            </li>
          </ul>
        )}
      </div>
    </nav>
  );
}

export default App;
