'use strict'
import React from 'react'

import './EnterButton.css'

const EnterButton = ({label, display, forceHidden, onClick}) => (

    <div className={`enter_button_container ${display && !forceHidden? '' : 'active'}`}>
        <a className="enter_button" onClick={() => onClick()}>{label}</a>
    </div>
)

export default EnterButton
