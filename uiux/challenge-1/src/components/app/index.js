import React, {
  Component
} from 'react';
import 'components/app/index.css';
// Store
import store from 'store';
// Components
import ArtistSearch from 'components/container/artist-search';

const {
  Provider
} = store;

class App extends Component {

  render () {
    return (
      <Provider>
        <div className="App">
          <ArtistSearch/>
        </div>
      </Provider>
    );
  }
}

export default App;


