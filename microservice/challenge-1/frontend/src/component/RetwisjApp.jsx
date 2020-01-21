import { Link, Route, BrowserRouter as Router, Switch } from 'react-router-dom'
import React, { Component } from 'react';

import HomeComponent from './HomeComponent';
import UserComponent from './UserComponent';
import UsersComponent from './UsersComponent';

class RetwisjApp extends Component {
    render() {
        return (
            <Router>
                <>
                    <h1>Retwis</h1>
                    <Link to="/">Home</Link> | <Link to="/users">Users</Link>
                    <br />
                    <Switch>
                        <Route path="/" exact component={HomeComponent} />
                        <Route path="/users/:username" component={UserComponent} />
                        <Route path="/users" component={UsersComponent} />
                    </Switch>
                </>
            </Router>
        )
    }
}

export default RetwisjApp