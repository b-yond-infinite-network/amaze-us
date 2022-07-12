import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
// import registerServiceWorker from './registerServiceWorker';
import {Provider} from 'react-redux'
import store from './redux/Store'
require('dotenv').config()
const createStore=store()
ReactDOM.render(<Provider store={createStore}><App /></Provider>, document.getElementById('root'));
