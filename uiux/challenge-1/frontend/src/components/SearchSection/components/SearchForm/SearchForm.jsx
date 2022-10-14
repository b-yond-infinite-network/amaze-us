import React, { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import styles from './styles.module.css';

export const SearchForm = ({ onSearch }) => {
  const [loading, setLoading] = useState(false);
  const [lastSubmit, setLastSubmit] = useState(null);
  const [query, setQuery] = useState('');
  const [showHint, setShowHint] = useState(false);
  const [error, setError] = useState('');

  const hintTimeoutRef = useRef(null);

  const handleSubmit = async e => {
    e.preventDefault();
    clearTimeout(hintTimeoutRef.current);
    setShowHint(false);
    if (lastSubmit === query) return;
    setLastSubmit(query);
    setLoading(true);
    try {
      await onSearch(query);
    } catch (err) {
      setShowHint(true);
      setLastSubmit(null);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    setShowHint(false);
    if (query) {
      hintTimeoutRef.current = setTimeout(() => {
        setShowHint(true);
      }, 1000);
    }
    return () => clearTimeout(hintTimeoutRef.current);
  }, [query]);

  return (
    <div className={styles.wrapper}>
      <form className={styles.form} onSubmit={handleSubmit}>
        <button
          type="submit"
          className={`${styles.button} ${loading ? styles.loading : ''}`}
        />
        <input
          className={styles.input}
          type="search"
          name="query"
          onChange={e => setQuery(e.target.value)}
          value={query}
        />
      </form>
      {showHint && (
        <div className={`${styles.hint} ${error ? styles.error : ''}`}>
          {error || 'Press search button or Enter to start searching'}
        </div>
      )}
    </div>
  );
};

SearchForm.propTypes = {
  onSearch: PropTypes.func.isRequired,
};
