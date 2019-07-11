import React from 'react';
import { Jumbotron, Button, Input } from 'reactstrap';
import searchArtist from '../services/addressSearch';

export default class AppLayout extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      currentArtist: '',
    }
  }

  onArtistChange = (event) => {
    this.setState({
      currentArtist: event.target.value,
    });
  }

  getSearchResults = async () => {
    const { currentArtist } = this.state;
    await searchArtist(currentArtist);
  }

  render() {
    return(
      <Jumbotron>
        <h3>Welcome to my party!</h3>
        <div>To start having some fun, search up the name of an artist below!</div>
        <hr />
        <Input 
          name="artist"
          label="Search Artist"
          value={this.state.currentArtist}
          placeholder="Red Hot Chili Peppers"
          onChange={this.onArtistChange}
        />
        <br />
        <Button color="primary" onClick={this.getSearchResults}>Search!</Button>
      </Jumbotron>
    )
  }
}