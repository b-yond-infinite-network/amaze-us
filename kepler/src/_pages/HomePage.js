import React, { Component } from 'react'
import { Link } from 'react-router-dom'

export default function HomePage() {
    return (
        <div className="container">
            <h1>Home Page</h1>
            <p>
                Go to <Link to="/">login</Link> page.
            </p>
            <p>
                Go to <Link to="/resource">resource</Link> page.
            </p>
            <p>
                Go to <Link to="/variation">variation</Link> page.
            </p>
            <p>
                Go to <Link to="/pioneer">pioneer</Link> page.
            </p>
            <p>
                Go to <Link to="/administration">administration</Link> page.
            </p>
        </div>
    )
}