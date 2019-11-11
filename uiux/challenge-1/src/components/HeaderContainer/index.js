import React from 'react';
import './headerContainer.css'

const HeaderContainer = (props) => {
    return (
        <div className="app-header">
            {props.children}
        </div>
    );
}

export default HeaderContainer;
