import * as React from 'react';
import * as ReactDOM from 'react-dom';

import Header from './components/header';
import SearchBar from './components/search-bar';

ReactDOM.render(<div>
    <Header />
    <SearchBar />
</div>, document.getElementById('root'));
