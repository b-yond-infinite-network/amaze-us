import React from 'react';
import PropTypes from 'prop-types';

const Input = (props) => {
    const { type, value, className, placeholder, onChange, onKeyPressed } = props;
    return (
        <input
            type={type}
            value={value}
            className={className}
            placeholder={placeholder}
            onChange={onChange}
            onKeyPress={onKeyPressed}
        />
    );
}

Input.propTypes = {
    type: PropTypes.string,
    value: PropTypes.string,
    className: PropTypes.string,
    placeholder: PropTypes.string,
    onChange: PropTypes.func,
    onKeyPressed: PropTypes.func,
}

Input.defaultProps = {
    type: "text"
}
export default Input;