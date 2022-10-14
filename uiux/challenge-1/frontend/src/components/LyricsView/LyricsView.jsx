import React from 'react';
import PropTypes from 'prop-types';
import styles from './styles.module.css';

export const LyricsView = ({
  lyrics,
  copyright,
  trackingUrl,
  artistName,
  trackName,
}) => (
  <div className={styles.container}>
    <h2>
      <span>{artistName}</span>
      {' '}
      -
      {' '}
      <span>{trackName}</span>
    </h2>
    <pre className={styles.lyrics}>{lyrics}</pre>
    <p className={styles.copyright}>{copyright}</p>
    <script src={trackingUrl} />
  </div>
);

LyricsView.propTypes = {
  lyrics: PropTypes.string,
  copyright: PropTypes.string,
  trackingUrl: PropTypes.string,
  artistName: PropTypes.string,
  trackName: PropTypes.string,
};
