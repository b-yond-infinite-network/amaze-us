import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';

import store from 'store/config';

import Layout from 'components/Layout';
import Home from 'pages/Home';

import './app.scss';

const routes = [{ elem: <Home />, path: '/', index: true }];

function App() {
  return (
    <Provider store={store}>
      <BrowserRouter>
        <Routes>
          <Route path={'/'} element={<Layout />}>
            {routes.map(route => (
              <Route key={route.path} index={route.index} path={route.path} element={route.elem} />
            ))}
          </Route>
        </Routes>
      </BrowserRouter>
    </Provider>
  );
}

export default App;
