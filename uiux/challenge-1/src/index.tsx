import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./layouts/App";
import { Router } from "./router";

ReactDOM.render(
  <App>
    <Router />
  </App>,
  document.getElementById("root")
);
