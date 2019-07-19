import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import AppLayout from './Components/AppLayout';
import styles from './Components/styles';
import injectSheet from 'react-jss'
import * as serviceWorker from './serviceWorker';
import 'bootstrap/dist/css/bootstrap.min.css';

const StyledApp = injectSheet(styles)(AppLayout);

ReactDOM.render(<StyledApp />, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
