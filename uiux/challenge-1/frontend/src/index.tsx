/// <reference path='./index.d.ts'/>

import * as React from "react";
import * as ReactDOM from "react-dom";
import App from "./App";
import "./styles/fonts/fonts.css";

ReactDOM.render(<App />, document.getElementById("app"));

// Hot Module Replacement
declare let module: { hot: any };

if (module.hot) {
  module.hot.accept("./App", () => {
    const NewApp = require("./App").default;

    ReactDOM.render(<NewApp />, document.getElementById("app"));
  });
}
