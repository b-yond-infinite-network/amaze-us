import React, {Component} from 'react';
import {BrowserRouter as Router} from 'react-router-dom';
import Container from 'react-bootstrap/Container';
import Menu from './components/Menu';
import Header from './components/Header';
import Users from './components/Users';

class App extends Component {

  render() {
    return (
      <Router>
        <React.Fragment>
          <Container>
            <Header/>
            <Menu/>
            <Users/>
          </Container>
        </React.Fragment>
      </Router>
    );
  }

}

export default App;
