import React, {
  Component
} from 'react';
// Store
import store from 'store';
// Components
import ArtistSearch from 'components/container/artist-search';
import ArtistView from 'components/container/artist-view';

const {
  Provider,
  connect
} = store;

const BackgroundProvider = connect(state => ({
  selectedArtist: state.selectedArtist,
  selectedTrack: state.selectedTrack,
}))(({
  selectedArtist,
  selectedTrack,
  children
}) => {
  const stage = selectedTrack
    ? 'karaoke-bg-iii'
    : selectedArtist
      ? 'karaoke-bg-ii'
      : 'karaoke-bg-i';
  return (
    <div 
      className={`karaoke-bg ${stage}`}
      style={{
        minHeight: '100%'
      }}
    >
      {children}
    </div>
  );
});

class App extends Component {

  render () {
    return (
      <Provider>
        <BackgroundProvider>
          <div style={{
            padding: '1rem'
          }}>
            <h1 style={{
              color: '#fff',
              fontSize: '8rem',
              lineHeight: '8rem',
              margin: 0,
              textShadow: '-.25rem .25rem 0 rgba(0,0,0, .5)'
            }}>Karaoke!</h1>
            <ArtistSearch/>
            <ArtistView/>
          </div>
        </BackgroundProvider>
      </Provider>
    );
  }
}

export default App;


