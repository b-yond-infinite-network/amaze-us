import React from 'react';
import PropTypes from 'prop-types';

import './Logo.css';
const Logo = (props) => {
    return (
        <div className="header-logo">
            <img
                className="header-logo-img"
                alt="This is the logo of the company"
                src={props.src}
                onClick={props.clickTo}
            />
        </div>
    );
}
Logo.propTypes = {
    src: PropTypes.string,
    clickTo: PropTypes.func,
}

Logo.defaultProps = {
    src:process.env.PUBLIC_URL + "/images/b-yond.png"
}

export default Logo;