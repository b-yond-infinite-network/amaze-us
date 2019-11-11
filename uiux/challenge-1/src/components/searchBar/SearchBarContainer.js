import React, { useState } from 'react';
import history from './../../router/history';
import SearchBar from './SearchBar';
import PropTypes from 'prop-types';

const SearchBarContainer = (props) => {
    const [keyword, setKeyWord] = useState(props.currentValue);
    const { searchBarPosition } = props;

    const setKeyWordToState = (event) => {
        setKeyWord(event.target.value);
    }

    const onKeyPressed = (event) => {
        if (event.key === "Enter") {
            launchSearch();
        }
    }

    const launchSearch = () => {
        history.push("/artists/" + keyword);
    }

    return (
        <SearchBar
            value={keyword}
            setKeyWord={setKeyWordToState}
            launchSearch={launchSearch}
            onKeyPressed={onKeyPressed}
            className={searchBarPosition} />
    );
}

SearchBarContainer.propTypes = {
    currentValue: PropTypes.string,
    className: PropTypes.string
}

SearchBarContainer.defaultProps = {
    currentValue: "",
    className: ""
}

export default SearchBarContainer;