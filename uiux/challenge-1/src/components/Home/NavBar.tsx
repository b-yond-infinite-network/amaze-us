import React from "react";
import { Link } from "react-router-dom";

export const NavBar = () =>
    <nav className="navbar navbar-dark">
        <Link to="/" className="navbar-brand">Karaoke App<span className="sr-only">Karaoke App</span></Link>
        <li className="nav-item mr-3">
            <Link to="/" style={{ textDecoration: "none" }}>Search Artists<span className="sr-only">Search Artists</span></Link>
        </li>
    </nav>