import React, { Component } from 'react';
import 'components/app/index.css';



class App extends Component {
  render() {
    return (
      <div className="App">
        <ul>
          <li>an artist search bar - artist.get</li>
          <li>list of songs</li>
          <li>ability to sort the songs by
            <ul>
              <li>the number of lyrics they have</li>
              <li>title</li>
              <li>duration</li>
            </ul>
          </li>
          <li>You will want, of course, to be able to display the song lyrics themselves.</li>
          <li>Connect to Spotify API</li>
          <li>Typeahead input</li>
          <li>Get artist image from somewhere?</li>
        </ul>

        <h3>To do</h3>
        <ul>
          <li>Pass apikey via configuration</li>
          <li>Unit tests</li>
        </ul>
      </div>
    );
  }
}

export default App;