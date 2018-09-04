// Author: Anthony Guevara
// E-mail: anthony.guev@gmail.com
// Date: May 27, 2018
// Navbar component

import React, { Component } from 'react';
import { Navbar, Grid } from 'react-bootstrap';

class NavBar extends Component {

    render() {
        return (
            <Navbar inverse fixedTop>
                <Grid>
                    <Navbar.Header>
                        <Navbar.Brand>
                        <a href="/">Karaoke Lyrics <span role="img"aria-label="microphone">🎤</span></a>
                        </Navbar.Brand>
                        <Navbar.Toggle />
                    </Navbar.Header>
                </Grid>
            </Navbar>
        )
    }
}

export default NavBar;