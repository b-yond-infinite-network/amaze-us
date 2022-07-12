import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import Navbar from "./components/layout/Navbar";
import Index from "./components/layout/Index";
import Lyrics from "./components/tracks/Lyrics";

import "./App.css";

import { ContextController } from "./components/layout/context";

const App = () => {
  return (
    <ContextController>
      <Router>
        <>
          
          <div className="heroBg">
          <Navbar/>
            <div className="container">
              <Switch>
                <Route exact path="/" component={Index} />
                <Route exact path="/lyrics/track/:id" component={Lyrics} />
              </Switch>
            </div>
          </div>
          
        </>
      </Router>
    </ContextController>
  );
};

export default App;
