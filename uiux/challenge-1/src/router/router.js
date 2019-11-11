import React from 'react';
import { Router, Route, Switch } from 'react-router-dom';

import history from './history';
import Home from './../pages/Home';
import Artists from './../pages/Artists';
import Tracks from './../pages/Tracks';
import Lyrics from './../pages/Lyrics';

export const costumRouter = () => {
    return (
        <Router history={history}>
            <Switch>
                <Route path="/artists/:keyword" component={Artists} exact />
                <Route path="/artists/artist/:artist_name/tracks/" component={Tracks} exact />
                <Route path="/artists/artist/:artist_name/tracks/:track_id" component={Lyrics} exact />
                <Route path="/" component={Home}>
                </Route>
            </Switch>
        </Router>
    );
}
