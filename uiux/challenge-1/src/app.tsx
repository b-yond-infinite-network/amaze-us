import * as React from 'react';
import * as ReactDOM from 'react-dom';

import Header from './components/header';
import Search from './components/search';

import './assets/styles/main.scss';

ReactDOM.render(<div className="wrapper container-fluid">
    <Header /> 
    <Search />
</div>, document.getElementById('root'));
