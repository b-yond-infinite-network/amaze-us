import React, { Component } from 'react'
import { Link } from 'react-router-dom'

export default function ResourcePage() {
    return (
        <div className="container">
            <h1>Resource Page</h1>
            <p>
                Go to <Link to="/home">home</Link> page.
            </p>
        </div>
    )
}