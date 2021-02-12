import React from 'react';
import {BrowserRouter as Router, NavLink, Redirect, Route} from 'react-router-dom';
import './App.css';

import Pioneers from './Pages/Pioneers';
import Manage from './Pages/Manage';
import Home from './Pages/Home';
import Audit from './Pages/Audit';
import Login from './Pages/Login';
import {useDispatch, useSelector} from "react-redux";
import {logout} from "./Actions/auth";

function App() {
  const isLoggedIn = useSelector(state => state.authReducer.isLoggedIn);
  const dispatch = useDispatch();
  const logOut = () => {
    dispatch(logout());
  };


  return (
    <Router>
      <div>
        <Navbar isLoggedIn={isLoggedIn} logOut={logOut}/>

        <Route path='/login' data-test='login-route' exact component={Login}/>
        <PrivateRoute isLoggedIn={isLoggedIn} path='/' exact component={Home}/>
        <PrivateRoute isLoggedIn={isLoggedIn} path='/pioneers/' component={Pioneers}/>
        <PrivateRoute isLoggedIn={isLoggedIn} path='/manage/' component={Manage}/>
        <PrivateRoute isLoggedIn={isLoggedIn} path='/audit/' component={Audit}/>
      </div>
    </Router>
  );
}

const Navbar = ({isLoggedIn, logOut}) =>
  (<nav>
      <ul className='header'>
        {!isLoggedIn && (
          <li><NavLink aria-label='Login' to='/login' exact activeClassName='active'>Login</NavLink></li>)}

        {isLoggedIn && (
          <React.Fragment>
            <li><NavLink aria-label='Home' data-test='Home' to='/' exact activeClassName='active'>Home</NavLink></li>
            <li><NavLink aria-label='Pioneers' to='/pioneers/' exact activeClassName='active'>Pioneers</NavLink></li>
            <li><NavLink aria-label='Manage' to='/manage/' exact activeClassName='active'>Habitat and Survival
              Management</NavLink></li>
            <li><NavLink aria-label='Audit' to='/audit/' exact activeClassName='active'>Audit</NavLink></li>
            <li aria-label='Logout' className='logout'><a href='/' data-test='logout' onClick={logOut}>Logout</a></li>
          </React.Fragment>
        )}
      </ul>
    </nav>
  );

const PrivateRoute = ({component: Component, ...rest}) => (
  <Route {...rest} render={(props) => (rest.isLoggedIn ? <Component {...props} /> : <Redirect to='/login'/>
  )}/>);

export default App;
