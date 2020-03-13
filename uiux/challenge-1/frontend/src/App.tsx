import * as React from "react";
import styled from "styled-components";
import { HashRouter as Router, Route, Switch } from "react-router-dom";

// Importing pages
import LandingPage from "./pages/LandingPage";
import LyricsPage from "./pages/LyricsPage";

class App extends React.Component<{}, {}> {
  render(): JSX.Element {
    return (
      <Router>
        <Switch>
          <Route exact path="/" component={LandingPage} />
          <Route exact path="/lyrics/" component={LyricsPage} />
        </Switch>
      </Router>
    );
  }
}

export default App;
