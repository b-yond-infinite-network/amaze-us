import * as React from "react";
import styled from "styled-components";
import { HashRouter as Router, Route, Switch } from "react-router-dom";

// Importing pages
import LandingPage from "./pages/LandingPage";

class App extends React.Component<{}, {}> {
  render(): JSX.Element {
    return (
      <Router>
        <Switch>
          <Route exact path="/" component={LandingPage} />
        </Switch>
      </Router>
    );
  }
}

export default App;
