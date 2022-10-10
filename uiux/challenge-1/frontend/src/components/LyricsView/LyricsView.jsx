import React from 'react';
import PropTypes from 'prop-types';
import styles from './styles.module.css';

export const LyricsView = ({
  lyrics: {
    lyrics: {
      lyrics_body,
      lyrics_copyright,
      script_tracking_url,
    },
  },
  artistName,
  trackName,
}) => (
  <div className={styles.container}>
    <h2>
      {artistName}
      {' '}
      -
      {' '}
      {trackName}
    </h2>
    <pre className={styles.lyrics}>{lyrics_body}</pre>
    <p className={styles.copyright}>{lyrics_copyright}</p>
    <script src={script_tracking_url} />
  </div>
);

LyricsView.propTypes = {
  lyrics: PropTypes.shape({
    lyrics: PropTypes.shape({
      lyrics_body: PropTypes.string,
      lyrics_copyright: PropTypes.string,
      script_tracking_url: PropTypes.string,
    }),
  }).isRequired,
  artistName: PropTypes.string,
  trackName: PropTypes.string,
};
