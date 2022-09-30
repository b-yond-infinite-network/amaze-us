import React from 'react';
import styles from './styles.module.css';

export const SearchOptions = (
  {
    options = [],
    onSelect,
    value: currentValue,
  },
) =>
  <div className={styles.container}>
    {options.map(({ key, name, value }) =>
      (
        <button
          className={`${styles.option} ${value === currentValue ? styles.selected : ''}`}
          key={key}
          onClick={() => onSelect(value)}
        >
          {name}
        </button>
      ))}
  </div>;
