import React, {Component} from 'react';
import {connect} from 'react-redux'
import {Redirect} from 'react-router-dom';
import {login} from '../Actions/auth';

let createHandlers = function (dispatch) {
  let loginOnSubmit = function (username, password) {
    dispatch(login(username, password))
      .catch(() => {
      });
  };

  return {loginOnSubmit};
};


class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: '',
      password: '',
      errorUsername: '',
      errorPassword: '',
    };
    this.handlers = createHandlers(this.props.dispatch);
  }

  handleSubmit = (evt) => {
    evt.preventDefault();
    this.setState({errorUsername: this.state.username ? '' : 'Username is required'});
    this.setState({errorPassword: this.state.password ? '' : 'Password is required'});


    if (this.state.username && this.state.password) {
      this.handlers.loginOnSubmit(this.state.username, this.state.password)
    }
  };

  handleUserChange = (evt) => {
    this.setState({
      username: evt.target.value,
      errorUsername: ''
    });
  };

  handlePassChange = (evt) => {
    this.setState({
      password: evt.target.value,
      errorPassword: ''
    });
  };

  render() {
    if (this.props.isLoggedIn) {
      return <Redirect to='/'/>;
    } else {
      return (
        <div className='content'>
          <div className='login'>
            <form onSubmit={this.handleSubmit}>
              <label className='login-input'><b>Username</b></label>
              <input type='text'
                     data-test='username'
                     className='login-input'
                     placeholder='Enter Username'
                     value={this.state.username} onChange={this.handleUserChange}/>
              {this.state.errorUsername && <span className='error'>{this.state.errorUsername}</span>}
              <label className='login-input'><b>Password</b></label>
              <input type='password'
                     className='login-input'
                     data-test='password'
                     placeholder='Enter Password'
                     value={this.state.password} onChange={this.handlePassChange}/>
              {this.state.errorPassword && <span className='error'>{this.state.errorPassword}</span>}
              <div className='flex-container'>
                <button type='submit'
                        aria-label='login'
                        className='login-button'
                        data-test='submit'>Log in
                </button>
              </div>
            </form>
          </div>
        </div>
      );
    }
  }
}

const mapStateToProps = state => {
  return {
    isLoggedIn: state.authReducer.isLoggedIn,
  };
};


export default connect(mapStateToProps)(Login);
