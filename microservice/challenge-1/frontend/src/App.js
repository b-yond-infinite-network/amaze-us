import React from "react";
import propTypes from 'prop-types'

const App = ({ children }) => <div>{children}</div>

App.propTypes = {
    children: propTypes.element
}

export default App