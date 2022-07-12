import React, { useState, useEffect } from "react";
import { BrowserRouter, Switch, Route, NavLink } from "react-router-dom";

import Login from "./compenents/auth/Login";
import Home from "./compenents/home/Home";

import Logout from "./compenents/auth/Logout";
import DriversList from "./compenents/driver/DriversList";
import BusesList from "./compenents/bus/BusesList";
import PrivateRoute from "./Utils/PrivateRoute";
import PublicRoute from "./Utils/PublicRoute";
import { getToken } from "./Utils/Common";

function App() {
  const [authLoading, setAuthLoading] = useState(true);
  const token = getToken();
  useEffect(() => {
    const token = getToken();

    if (!token) {
      return;
    }

    setAuthLoading(false);
  }, []);

  if (authLoading && getToken()) {
    return <div className="content">Checking Authentication...</div>;
  }

  return (
    <div className="App">
      <BrowserRouter>
        <div>
          {token ? (
            <div className="header">
              <NavLink exact activeClassName="active" to="/">
                Home
              </NavLink>

              <NavLink activeClassName="active" to="/drivers">
                Drivers
              </NavLink>
              <NavLink activeClassName="active" to="/buses">
                Buses
              </NavLink>

              <NavLink activeClassName="active" to="/logout">
                Logout
              </NavLink>
            </div>
          ) : (
            <div className="header">
              <NavLink exact activeClassName="active" to="/">
                Home
              </NavLink>
            </div>
          )}

          <div className="content">
            <Switch>
              <Route exact path="/" component={Home} />
              <PublicRoute path="/login" component={Login} />
              <PrivateRoute exact path="/home" component={Home} />
              <PrivateRoute path="/drivers" component={DriversList} />
              <PrivateRoute path="/buses" component={BusesList} />
              <PrivateRoute path="/logout" component={Logout} />
            </Switch>
          </div>
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
