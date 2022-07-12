import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import 'bootstrap/dist/css/bootstrap.min.css';

 window.$baseURL         = "http://localhost:6868/api/v1/";
//window.$baseURL         = "http://localhost:8080/api/v1/";
ReactDOM.render(<App />, document.getElementById('root'));