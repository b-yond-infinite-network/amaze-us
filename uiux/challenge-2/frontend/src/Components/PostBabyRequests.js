import React from 'react';
import axios from 'axios';
import Snackbar from "./Snackbar";


export default class PostBabyRequests extends React.Component {
  snackbarRef = React.createRef();

  constructor(props) {
    super(props);
    this.state = {
      name: '',
      error: '',
      snackbar: false,
      disabled: true
    };
  }

  sendSnackbar = (notification) => {
    this.snackbarRef.current.openSnackBar(notification)
  };

  postRequest = (event) => {
    event.preventDefault();
    axios.post('/v1/baby/request', {name: this.state.name})
      .then((response) => {
          if (response.status === 201) {
            this.sendSnackbar('You have submitted a baby request for ' + this.state.name);
            this.setState({name: '', snackbar: true});
            document.getElementById('post-request-form').reset();
          } else {
            this.sendSnackbar('Your baby request for ' + this.state.name + ' did not work please try again');
          }
        },
        (error) => {
          this.sendSnackbar('There was an error with the server, ' + error);
        }
      )

  };

  nameChangeHandler = (event) => {
    event.preventDefault();
    const {value} = event.target;
    const illegalCharacters = '0123456789+-*/\\|][{};:"?><,!@#$%^&';
    this.setState({name: value});
    if (value.trim().length <= 2 || value.split('').filter(c => !illegalCharacters.includes(c)).length !== value.length) {
      this.setState({error: 'Name must be at least 3 characters long, no trolling!', disabled: true})
    } else {
      this.setState({error: '', disabled: false})
    }

  };

  render() {
    const {error} = this.state;
    return (
      <div>
        <form
          title='post-request-form'
          id='post-request-form'
          onSubmit={this.postRequest}>
          <p>Enter the name of the desired Baby:</p>
          <div>
            <input
              type='text'
              aria-label='name'
              placeholder='Baby name'
              onChange={this.nameChangeHandler}
            />
            <input
              type='submit'
              aria-label='sendRequest'
              value='Request'
              disabled={this.state.disabled}
            />
            {error !== '' && <span className='error'>{error}</span>}
          </div>
        </form>
        <Snackbar ref={this.snackbarRef}/>
      </div>
    );
  }
}

