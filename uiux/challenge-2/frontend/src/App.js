import React from "react";
import {BrowserRouter as Router, NavLink, Route} from "react-router-dom";
import './App.css';

import Pioneers from "./Pages/Pioneers";
import Manage from "./Pages/Manage";
import Home from "./Pages/Home";

function App() {
  return (
    <Router>
      <div>
        <nav>
          <ul className='header'>
            <li><NavLink aria-label="Home" to="/" exact activeClassName="active">Home</NavLink></li>
            <li><NavLink aria-label="Pioneers" to="/pioneers/" exact activeClassName="active" >Pioneers</NavLink></li>
            <li><NavLink aria-label="Manage" to="/manage/" exact activeClassName="active">Habitat and Survival Management</NavLink></li>
          </ul>
        </nav>

        <Route path="/" exact component={Home}/>
        <Route path="/pioneers/" component={Pioneers}/>
        <Route path="/manage/" component={Manage}/>
      </div>
    </Router>
  );
}

export default App;
