'use strict'
import React from 'react'

import './NotLoginLabel.css'

const NotLoginLabel = ({forceHidden, display, hasTried = false}) => (

    <div className={`not_login_in_label_container ${hasTried ? 'show' : ''} ${display && !forceHidden? '' : 'active'}`}>
        <a className="not_login_in_label">You don't have the privilege to access the page requested </a>
    </div>
)

export default NotLoginLabel
