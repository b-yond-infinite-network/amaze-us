import React from 'react';
import { BrowserRouter, Switch, Route, Link } from 'react-router-dom';
import './App.scss';
import { NavBar } from './components/Home/NavBar';
import { ArtistContainer } from './containers/ArtistContainer';
import { ArtistSearch } from './containers/SearchContainer';
import { TrackContainer } from './containers/TrackContainer';

export default function App() {

  return (
    <>
      <main>
        {!process.env.REACT_APP_API_KEY ?
          <h1>NO API KEY FOUND. Check instructions in pull request.</h1>
          :
          <BrowserRouter>
            <NavBar />
            <div style={{ display: "flex", flexDirection: "column", justifyContent: "center" }}>
              <div style={{ maxWidth: "1280px", alignSelf: "center" }}>
                <Switch>
                  <Route exact path="/" component={ArtistSearch} />
                  <Route exact path="/artist/:id" component={ArtistContainer} />
                  <Route exact path="/artist/:artist_id/:track_id" component={TrackContainer} />
                </Switch>
              </div>
            </div>
          </BrowserRouter>
        }
      </main >
    </>
  );
}