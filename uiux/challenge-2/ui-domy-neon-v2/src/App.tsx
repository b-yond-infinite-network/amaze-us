import React from 'react';
import { Provider } from 'react-redux';
import { Route, Router, Switch } from 'react-router-dom';

import store from './store';
import history from './services/history';
import PrivateRoute from './components/private-route';
import LoginPage from './pages/login';
import MainApp from './pages/main-app';

function App() {
  return (
    <Provider store={store}>
      <Router history={history}>
        <Switch>
          <Route exact={true} path='/login' component={LoginPage}/>
          <PrivateRoute path="/" component={MainApp}/>
        </Switch>
      </Router>
    </Provider>
  );
}

export default App;
