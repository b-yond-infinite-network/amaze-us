import React from 'react';
import PropTypes from 'prop-types';
import styles from './styles.module.css';

export const SearchOptions = ({
  options = [],
  onSelect,
  value: currentValue,
}) => (
  <div className={styles.container}>
    {options.map(({
      key,
      name,
      value,
    }) => (
      <button
        type="button"
        className={`${styles.option} ${
          value === currentValue ? styles.selected : ''
        }`}
        key={key}
        onClick={() => onSelect(value)}
      >
        {name}
      </button>
    ))}
  </div>
);

SearchOptions.propTypes = {
  options: PropTypes.arrayOf(PropTypes.shape({
    key: PropTypes.string,
    name: PropTypes.string,
    value: PropTypes.any,
  })),
  onSelect: PropTypes.func.isRequired,
  value: PropTypes.any,
};
