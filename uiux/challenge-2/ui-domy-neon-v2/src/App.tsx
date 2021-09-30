import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { Route, Router, Switch } from 'react-router-dom';

import history from './services/history';
import PrivateRoute from './components/private-route';
import LoginPage from './pages/login';
import MainApp from './pages/main-app';
import { initAuth } from './store/sagas/auth';

function App() {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(initAuth());
  }, [dispatch])
  return (
    <Router history={history}>
      <Switch>
        <Route exact={true} path='/login' component={LoginPage}/>
        <PrivateRoute path="/" component={MainApp}/>
      </Switch>
    </Router>
  );
}

export default App;
