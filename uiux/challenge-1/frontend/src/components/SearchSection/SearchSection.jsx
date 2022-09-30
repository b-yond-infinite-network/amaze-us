import React, { useState } from 'react';
import { SearchForm } from './components/SearchForm';
import { SearchOptions } from './components/SearchOptions';
import styles from './styles.module.css';

export const SearchSection = (
  {
    onSearch,
    value,
    setValue,
    title,
  }) => {
  const [options, setOptions] = useState([]);

  const handleSearch = async query => setOptions(await onSearch(query));

  return <div className={`${styles.searchSection}`}>
    <button
      className={styles.clearButton}
      onClick={() => setValue(null)}
      disabled={value === null}
    >
      Clear
    </button>
    <h2>{title}</h2>
    <SearchForm onSearch={handleSearch} />
    <SearchOptions options={options} onSelect={setValue} value={value} />
  </div>;
};
