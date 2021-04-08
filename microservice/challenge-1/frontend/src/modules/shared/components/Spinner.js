import React from 'react'
import PropTypes from 'prop-types'
import { ClipLoader } from 'react-spinners'
import './Spinner.css'

export default function Spinner(props) {
    const { loading, size, color } = props
    return (
        loading && <div className='spinner'>
            <ClipLoader
                size={size}
                color={color}
                loading={loading}
            />
        </div>
    )
}

Spinner.propTypes = {
    loading: PropTypes.bool.isRequired,
    color: PropTypes.string.isRequired,
    size: PropTypes.number.isRequired
}

Spinner.defaultProps = {
    loading: false,
    color: '#ec0000',
    size: 30
}
