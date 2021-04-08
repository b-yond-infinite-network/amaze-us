import React, { useState } from "react";
import { Provider } from "react-redux";
import ReactDOM from "react-dom";
import App from "./App";
import { store } from "./store";
import * as serviceWorker from "./serviceWorker";
import "./config/AxiosConfig";
import { Switch, BrowserRouter, withRouter } from 'react-router-dom'
import AppRoute from './AppRoute'
import ApplicationRoutes from './Routes'
import Spinner from "./modules/shared/components/SpinnerContainer";
import "./index.css";

const { Routes } = ApplicationRoutes

const BodyComponent = ({ location: { pathname } }) => {
  const [extraPropsHeader, setExtraPropsHeader] = useState({ counter: null })

  return (
    <Switch>
      {Routes.map((route) => (
        <AppRoute {...route} extraPropsHeader={setExtraPropsHeader} />
      ))}
    </Switch>
  )
}

const Body = withRouter(BodyComponent)

ReactDOM.render(
  <Provider store={store}>
    <BrowserRouter>
      <div>
        <Spinner />
        <App>
          <Body key='body' />
        </App>
      </div>
    </BrowserRouter>
  </Provider>,
  document.getElementById("root")
);

serviceWorker.unregister();
