import React, {Component} from 'react';
import {BrowserRouter as Router} from 'react-router-dom';
import Container from 'react-bootstrap/Container';
import Menu from './components/Menu';
import Header from './components/Header';
import Users from './components/Users';

import UserService from './services/UserService';

class App extends Component {

  state = {
    users: []
  };

  componentDidMount() {
    UserService.get().then(users => { this.setState({users}) })
  }

  render() {
    return (
      <Router>
        <React.Fragment>
          <Container>
            <Header/>
            <Menu/>
            <Users users={this.state.users} />
          </Container>
        </React.Fragment>
      </Router>
    );
  }

}

export default App;
