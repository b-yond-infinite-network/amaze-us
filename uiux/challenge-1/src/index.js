// Libraries
import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
// Components
import App from 'components/app';
// Services
import MusixmatchProvider from 'api/musixmatch-provider';

const musixmatchProvider = new MusixmatchProvider({
  apiKey: '4148caebc14fa40fdc1b7fa3b3aced63'
});

ReactDOM.render(
  <App
    musixmatchProvider={musixmatchProvider}
  />,
  document.getElementById('root')
);