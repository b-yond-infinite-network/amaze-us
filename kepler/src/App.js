import React, {Component} from 'react';
import axios from 'axios';
import { BrowserRouter as Router, Route, Redirect} from "react-router-dom";

import HomePage from './_pages/HomePage';
import LoginPage from './_pages/LoginPage';
import PioneerPage from './_pages/PioneerPage';
import VariationPage from './_pages/VariationPage';
import ResourcePage from './_pages/ResourcePage';
import AdministrationPage from './_pages/AdministrationPage';

import './App.css';

const ping = {

  lastPing : false,

  lastTimeStamp : '',
  lastTimeStampUnit : '',

  timerPing : null,
  timerInterval : 5000,
}

const authentificationObtained = {

  isAuthenticated: false,

  isPioneer: false,
  isChecker: false, 
  isAdmin: false,

  pioneer: undefined,

  getIsAuthentificated() {
    return this.isAuthenticated;
  },
  reset() {

    this.isAuthenticated = false;

    this.isPioneer = false;
    this.isChecker = false;
    this.isAdmin = false;

    this.pioneer = undefined;
  },
  authenticate(level) {

    this.isAuthenticated = true;

    if (level >= 1) {
      this.isPioneer = true;
    }
    if (level >= 2) {
      this.isChecker = true;
    }
    if (level >= 3) {
      this.isAdmin = true;
    }
  },
  logout() {

    this.isAuthenticated = false;

    this.isPioneer = false;
    this.isChecker = false;
    this.isAdmin = false;
  }
}

const PioneerRoute = ({ component: Component, ...rest }) => (
  <Route {...rest} render={(props) => (authentificationObtained.isAuthenticated && authentificationObtained.isPioneer
      ? <Component {...props} />
      : <Redirect to={{
        pathname: '/',
        state: {tried: true}}}/>
  )} />
)

const CheckerRouter = ({ component: Component, ...rest }) => (
    <Route {...rest} render={(props) => (authentificationObtained.isAuthenticated && authentificationObtained.isChecker
      ? <Component {...props} />
      : <Redirect to={{
        pathname: '/',
        state: {tried: true}}}/>
  )} />
)

const AdministratorRouter = ({ component: Component, ...rest }) => (
  <Route {...rest} render={(props) => (authentificationObtained.isAuthenticated && authentificationObtained.isAdmin
      ? <Component {...props} />
      : <Redirect to={{
        pathname: '/',
        state: {tried: true}}}/>
  )} />
)

class App extends Component  {

  constructor (props) {
    super(props);

    /* bind the handle function to the object -> IMPORTANT */
    this.checkServerPing = this.checkServerPing.bind(this);
  }

  checkServerPing() {
    
    axios
      .get("http://localhost:8080/server/ping")
      .then(function(reponse) {

        ping.lastPing = true;
        ping.lastTimeStamp = reponse.data.timestamp;
        ping.lastTimeStampUnit = reponse.data.timestamp_unit;

      })
      .catch(error => {

        ping.lastPing = false;
        ping.lastTimeStamp = '';
        ping.lastTimeStampUnit = '';

        console.log(error)
      });
  }

  componentDidMount() {
    
    this.checkServerPing();
    if (ping.timerPing === undefined) {

      ping.timerPing = setInterval(() => {
        
        this.checkServerPing();

      }, ping.timerInterval);
    }
  }

  render() {
    
    return (
      <Router>

        <Route extact path="/" render={(props) => <LoginPage authentificationObtained={authentificationObtained} ping={ping} {...props} />} />

        <PioneerRoute path="/home" render={(props) => <HomePage ping={ping} {...props} />} />
        <PioneerRoute path="/pioneer" render={(props) => <PioneerPage authentificationObtained={authentificationObtained} ping={ping} {...props} />} />
        <PioneerRoute path="/variation" render={(props) => <VariationPage authentificationObtained={authentificationObtained} ping={ping} {...props} />} />
        <PioneerRoute path="/resource" render={(props) => <ResourcePage ping={ping} {...props} />} />

        <AdministratorRouter path="/administration" render={(props) => <AdministrationPage authentificationObtained={authentificationObtained} {...props} />} />

      </Router>
    )
  }
}

export default App
